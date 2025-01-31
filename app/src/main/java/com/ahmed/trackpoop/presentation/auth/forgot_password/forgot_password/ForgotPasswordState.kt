package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password

data class ForgotPasswordState(
    val email: String = "",
    val emailError: String? = "",
    val isLoading: Boolean = false,
    val error: String? = ""
)
