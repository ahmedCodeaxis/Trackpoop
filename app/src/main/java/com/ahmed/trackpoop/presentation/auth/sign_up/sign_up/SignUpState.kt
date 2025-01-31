package com.ahmed.trackpoop.presentation.auth.sign_up.sign_up

import android.net.Uri

data class SignUpState(
    val name: String = "",
    val nameError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val password: String = "",
    val passwordError: String? = null,

    val phone: String = "",
    val phoneError: String? = null,

    val image: Uri? = null,  // Changed from profilePicture to image
    val imageError: String? = null,  // Changed from profilePictureError to imageError

    val isLoading: Boolean = false,
    val error: String? = null
)
