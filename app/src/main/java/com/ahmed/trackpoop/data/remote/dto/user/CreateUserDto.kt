package com.ahmed.trackpoop.data.remote.dto.user

import android.net.Uri

data class CreateUserDto(
    val name: String,
    val email: String,
    val password: String,
    val phone: String,
    val image: Uri? = null  // Changed from profilePicture to image
)

