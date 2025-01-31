package com.ahmed.trackpoop.data.remote.dto.user

data class UpdatePassword(
    val currentPassword: String,
    val newPassword: String
)
