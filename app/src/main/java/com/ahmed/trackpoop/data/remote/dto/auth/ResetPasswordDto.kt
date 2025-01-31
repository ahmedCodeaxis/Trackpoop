package com.ahmed.trackpoop.data.remote.dto.auth

data class ResetPasswordDto(
    val email: String,
    val password: String,
)