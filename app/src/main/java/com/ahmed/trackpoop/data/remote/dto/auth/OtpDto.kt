package com.ahmed.trackpoop.data.remote.dto.auth

data class VerifyOtpDto (
    val email: String,
    val otpCode: String
)

data class ResendOtpDto (
    val email: String
)
