package com.ahmed.trackpoop.presentation.auth.forgot_password.reset_password

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.utils.validatePassword

class ResetPasswordViewModel(
    private val repository: AuthRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(ResetPasswordState())
        private set
    val email = savedStateHandle.get<String>("email")!!

    fun onPasswordChanged(newPassword: String) {
        state = state.copy(password = newPassword, passwordError = validatePassword(newPassword))
    }

    fun onConfirmPasswordChanged(newConfirmPassword: String) {
        state = state.copy(
            confirmPassword = newConfirmPassword,
            confirmPasswordError = validatePassword(newConfirmPassword)
        )
    }

    fun resetPassword(navController: NavController) {
        if (state.passwordError.isNullOrEmpty() && state.confirmPasswordError.isNullOrEmpty()) {

            if (state.password != state.confirmPassword) {
                Toast.makeText(
                    navController.context,
                    "Passwords do not match",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }

            state = state.copy(isLoading = true)

            viewModelScope.launch {
                val result = repository.resetPassword(email, state.password)
                state = if (result.isSuccessful()) {
                    state.copy(isLoading = false)
                } else {
                    state.copy(isLoading = false, error = result.message)
                }

                if (state.isLoading == false && state.error.isEmpty())
                    navController.navigate(Screen.SignInScreen.route)
                else
                    Toast.makeText(
                        navController.context,
                        state.error,
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }

}