package com.ahmed.trackpoop.presentation.auth.forgot_password.reset_password

data class ResetPasswordState(
    val password: String = "",
    val passwordError: String? = null,

    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val error: String = ""
)
