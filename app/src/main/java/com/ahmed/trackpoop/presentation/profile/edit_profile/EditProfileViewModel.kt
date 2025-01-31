package com.ahmed.trackpoop.presentation.profile.edit_profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.remote.dto.user.UpdateUserDto
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.utils.validateEmail
import com.ahmed.trackpoop.utils.validateName
import java.io.File
class EditProfileViewModel: ViewModel() {
    var state by mutableStateOf(EditProfileState())
        private set

    var token: String? = null

    fun setToken(context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val preferencesManager = PreferencesManager(sharedPreferences)
        token = preferencesManager.getUserToken()
    }

    fun onNameChanged(name: String) {
        state = state.copy(name = name, nameError = validateName(name))
    }

    fun onEmailChanged(email: String) {
        state = state.copy(email = email, emailError = validateEmail(email))
    }

    fun onPhoneChanged(phone: String) {
        state = state.copy(
            phone = phone,
            phoneError = validatePhone(phone)
        )
    }

    fun onImagePicked(uri: Uri?) {
        state = state.copy(imageUri = uri)
    }

    fun editProfile(navController: NavController, context: Context) {
        state = state.copy(
            nameError = null,
            emailError = null,
            phoneError = null,
            imageError = null,
            error = null
        )

        // Validate fields
        val nameError = validateName(state.name)
        val emailError = validateEmail(state.email)
        val phoneError = validatePhone(state.phone)

        // Add image validation like in SignUp
        if (state.imageUri == null && state.image == null) {
            state = state.copy(imageError = "Profile picture is required")
            return
        }

        if (nameError != null || emailError != null || phoneError != null) {
            state = state.copy(
                nameError = nameError,
                emailError = emailError,
                phoneError = phoneError
            )
            return
        }

        viewModelScope.launch {
            state = state.copy(isLoading = true)

            // Handle image upload if there's a new image
            val imageFile = state.imageUri?.let { uri ->
                try {
                    context.contentResolver.openInputStream(uri)?.use { input ->
                        File.createTempFile("profile", ".jpg", context.cacheDir).also { file ->
                            file.outputStream().use { output ->
                                input.copyTo(output)
                            }
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    state = state.copy(imageError = "Failed to process image")
                    return@launch
                }
            }

            // Pass context and token properly
            val result = UserRepositoryImpl.editProfile(
                UpdateUserDto(
                    name = state.name,
                    email = state.email,
                    phone = state.phone,
                    image = state.imageUri,
                ), context, token ?: ""
            )

            if (result.isSuccessful()) {
                state = state.copy(isLoading = false)
                navController.popBackStack()
                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, result.message, Toast.LENGTH_SHORT).show()
                state = state.copy(isLoading = false, error = result.message)
            }
        }
    }

    private fun validatePhone(phone: String): String? {
        if (phone.isBlank()) return null
        return when {
            !phone.matches(Regex("^[+]?[0-9]{8,15}$")) -> "Invalid phone number format"
            else -> null
        }
    }
}
