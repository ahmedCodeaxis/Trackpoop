package com.ahmed.trackpoop.data.remote.dto.auth

data class RefreshTokenDto (
    val refreshToken: String
)

data class RefreshTokenResponse (
    val token: String,
    val refreshToken: String
)