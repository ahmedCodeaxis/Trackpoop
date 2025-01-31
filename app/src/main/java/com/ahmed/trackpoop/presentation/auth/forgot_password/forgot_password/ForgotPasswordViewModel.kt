package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.utils.validateEmail

class ForgotPasswordViewModel(
    private val repository: AuthRepository
) : ViewModel() {
    var state by mutableStateOf(ForgotPasswordState())
        private set

    fun onEmailChanged(email: String) {
        state = state.copy(email = email, emailError = validateEmail(email))
    }

    fun forgotPassword(navigationController: NavController) {
        // Clear any previous errors
        state = state.copy(emailError = null, error = null)

        // Validate fields
        val emailError = validateEmail(state.email)

        if (emailError != null) {
            state = state.copy(emailError = emailError)
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val result = repository.forgotPassword(state.email)

            state = if (result.isSuccessful()) {
                state.copy(isLoading = false)
            } else {
                state.copy(isLoading = false, error = result.message)
            }

            if (state.isLoading == false && state.error.isNullOrEmpty()) {
                navigationController.navigate(Screen.ForgotPasswordOTPScreen.route + "/${state.email}")
            } else {
                Toast.makeText(
                    navigationController.context,
                    state.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}