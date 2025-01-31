package com.ahmed.trackpoop.data.remote.dto.user

import android.net.Uri

data class UpdateUserDto(
    val name: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val image: Uri? = null
)