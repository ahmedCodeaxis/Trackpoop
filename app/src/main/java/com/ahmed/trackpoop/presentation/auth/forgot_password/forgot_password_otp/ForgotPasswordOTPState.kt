package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password_otp

data class ForgotPasswordOTPState(
    val otpFields : List<String> = List(4) {""},
    val isLoading: Boolean = false,
    val error: String? = null
)
