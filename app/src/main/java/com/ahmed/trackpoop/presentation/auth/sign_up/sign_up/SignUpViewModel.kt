package com.ahmed.trackpoop.presentation.auth.sign_up.sign_up

import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.user.CreateUserDto
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.utils.validateEmail
import com.ahmed.trackpoop.utils.validateName
import com.ahmed.trackpoop.utils.validatePassword
import com.ahmed.trackpoop.utils.validatephone

class SignUpViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(SignUpState())
        private set

    fun onNameChanged(name: String) {
        state = state.copy(name = name)
    }

    fun onEmailChanged(email: String) {
        state = state.copy(email = email)
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password)
    }

    fun onphoneChanged(phone: String) {
        state = state.copy(phone = phone)
    }

    fun onImageSelected(uri: Uri?) {  // Changed from onProfilePictureSelected to onImageSelected
        state = state.copy(image = uri)
    }

    fun signUp(navigationController: NavController) {
        state = state.copy(
            nameError = null,
            emailError = null,
            passwordError = null,
            phoneError = null,
            imageError = null,  // Changed from profilePictureError to imageError
            error = null
        )

        // Validate fields
        val nameError = validateName(state.name)
        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)
        val phoneError = validatephone(state.phone)

        if (state.image == null) {  // Changed from profilePicture to image
            state = state.copy(imageError = "Profile picture is required")  // Changed error property name
            return
        }

        if (nameError != null || emailError != null || passwordError != null || phoneError != null) {
            state = state.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                phoneError = phoneError
            )
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val result = repository.signup(
                CreateUserDto(
                    name = state.name,
                    email = state.email,
                    password = state.password,
                    phone = state.phone,
                    image = state.image  // Changed from profilePicture to image
                )
            )
            state = if (result.isSuccessful()) {
                state.copy(isLoading = false)
            } else {
                state.copy(isLoading = false, error = result.message)
            }

            if (state.isLoading == false && state.error.isNullOrEmpty())
                navigationController.navigate(Screen.SignUpOTPScreen.route + "/${state.email}")
            else
                Toast.makeText(
                    navigationController.context,
                    state.error,
                    Toast.LENGTH_SHORT
                ).show()
        }
    }


}