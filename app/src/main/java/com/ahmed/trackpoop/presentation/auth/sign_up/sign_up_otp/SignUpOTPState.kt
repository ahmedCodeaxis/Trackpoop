package com.ahmed.trackpoop.presentation.auth.sign_up.sign_up_otp

data class SignUpOTPState(
    val otpFields : List<String> = List(4) {""},
    val isLoading: Boolean = false,
    val error: String? = null
)
