package com.ahmed.trackpoop.data.remote.dto.auth

data class LoginDto (
    val email: String,
    val password: String
)

data class LoginResponse (
    val token: String,
    val refreshToken: String
)