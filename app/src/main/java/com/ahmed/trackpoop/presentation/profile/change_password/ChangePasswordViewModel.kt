package com.ahmed.trackpoop.presentation.profile.change_password

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.repository.PoopRepositoryImpl
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.utils.validatePassword
class ChangePasswordViewModel : ViewModel() {
    var state by mutableStateOf(ChangePasswordState())
        private set

    // Removed unnecessary token property
    // You will retrieve the token directly when needed

    // Validate current, new, and confirm passwords
    fun onCurrentPasswordChanged(currentPassword: String) {
        state = state.copy(
            currentPassword = currentPassword,
            currentPasswordError = validatePassword(currentPassword)
        )
    }

    fun onNewPasswordChanged(newPassword: String) {
        state =
            state.copy(newPassword = newPassword, newPasswordError = validatePassword(newPassword))
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        state = state.copy(
            confirmPassword = confirmPassword,
            confirmPasswordError = validatePassword(confirmPassword)
        )
    }

    // Change password function
    fun changePassword(navController: NavController, context: Context) {
        // Start by showing loading and clearing any previous errors
        state = state.copy(
            isLoading = true,
            currentPasswordError = null,
            newPasswordError = null,
            confirmPasswordError = null
        )

        // Validate passwords
        val currentPasswordError = validatePassword(state.currentPassword)
        val newPasswordError = validatePassword(state.newPassword)
        val confirmPasswordError = validatePassword(state.confirmPassword)

        if (currentPasswordError != null || newPasswordError != null || confirmPasswordError != null) {
            state = state.copy(
                currentPasswordError = currentPasswordError,
                newPasswordError = newPasswordError,
                confirmPasswordError = confirmPasswordError,
                isLoading = false
            )
            return
        }

        if (state.newPassword != state.confirmPassword) {
            state = state.copy(
                confirmPasswordError = "Passwords do not match",
                isLoading = false
            )
            return
        }

        viewModelScope.launch {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val preferencesManager = PreferencesManager(sharedPreferences)
            val token = preferencesManager.getUserToken()

            if (token.isNullOrEmpty()) {
                Log.e("ChangePasswordViewModel", "Authorization token is missing")
                state = state.copy(isLoading = false, changePasswordError = "Authorization token is missing")
                return@launch
            }

            try {
                // Call the correct function and pass the actual token
                val result = UserRepositoryImpl.changePassword(state.currentPassword, state.newPassword, token, context)

                if (result.isSuccessful()) {
                    state = state.copy(isLoading = false)
                    navController.popBackStack()
                    Toast.makeText(navController.context, "Password changed successfully", Toast.LENGTH_SHORT).show()
                } else {
                    state = state.copy(isLoading = false, changePasswordError = result.message)
                    Toast.makeText(navController.context, result.message, Toast.LENGTH_SHORT).show()
                    Log.e("ChangePasswordViewModel", "Failed to change password: ${result.message}")
                }
            } catch (e: Exception) {
                state = state.copy(isLoading = false, changePasswordError = e.message)
                Toast.makeText(navController.context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                Log.e("ChangePasswordViewModel", "Error changing password: ${e.message}")
            }
        }
    }

}
