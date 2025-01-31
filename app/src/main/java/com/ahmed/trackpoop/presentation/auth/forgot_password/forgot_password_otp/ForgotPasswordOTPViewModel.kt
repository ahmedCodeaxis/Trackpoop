package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password_otp

import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.navigation.Screen

class ForgotPasswordOTPViewModel(
    private val authRepository: AuthRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    var state by mutableStateOf(ForgotPasswordOTPState())
        private set
    val email = savedStateHandle.get<String>("email")

    fun onOtpFieldChanged(index: Int, value: String, focusRequesters: List<FocusRequester>) {
        // Ensure only one digit is allowed
        if (value.length <= 1 && value.all { it.isDigit() }) {
            val otpFields = state.otpFields.toMutableList()
            otpFields[index] = value
            state = state.copy(otpFields = otpFields)
        }

        // Move focus to the next or hide the keyboard
        if (value.isNotEmpty()) {
            if (index < state.otpFields.size - 1) {
                focusRequesters[index + 1].requestFocus()
            } else {
                focusRequesters[index].freeFocus()
            }
        }

        if (value.isEmpty() && index > 0) {
            focusRequesters[index - 1].requestFocus()
        }
    }

    fun resendCode(navController: NavController) {
        state = state.copy(error = null, isLoading = true)

        viewModelScope.launch {
            if (!email.isNullOrEmpty()) {
                val result = authRepository.forgotPassword(email)
                state = if (result.isSuccessful()) {
                    state.copy(isLoading = false)
                } else {
                    state.copy(isLoading = false, error = result.message)
                }

                if (!state.isLoading && state.error.isNullOrEmpty()) {
                    Toast.makeText(
                        navController.context,
                        "OTP sent successfully",
                        Toast.LENGTH_SHORT
                    ).show()

                } else {
                    Toast.makeText(
                        navController.context,
                        state.error,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    fun verifyOtp(navController: NavController) {
        state = state.copy(error = null)

        // Validate fields
        val otp = state.otpFields.joinToString("")

        if (otp.length != 4) {
            Toast.makeText(
                navController.context,
                "Please enter a valid OTP",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (email.isNullOrEmpty()) {
            Toast.makeText(
                navController.context,
                "An error occurred. Please try again",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val result = authRepository.verifyOtp(email, otp)

            state = if (result.isSuccessful()) {
                state.copy(isLoading = false)
            } else {
                state.copy(isLoading = false, error = result.message)
            }

            if (state.isLoading == false && state.error.isNullOrEmpty()) {
                navController.navigate(Screen.ResetPasswordScreen.route + "/$email")
            } else {
                Toast.makeText(
                    navController.context,
                    state.error,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}