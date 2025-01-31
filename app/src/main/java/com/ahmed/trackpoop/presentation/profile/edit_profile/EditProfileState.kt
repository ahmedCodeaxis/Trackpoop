package com.ahmed.trackpoop.presentation.profile.edit_profile

import android.net.Uri

data class EditProfileState(
    val name: String = "",
    val nameError: String? = null,

    val email: String = "",
    val emailError: String? = null,

    val phone: String = "",
    val phoneError: String? = null,

    val image: String? = null,
    val imageUri: Uri? = null,
    val imageError: String? = null,  // Add this line

    val isLoading: Boolean = false,
    val error: String? = null
)