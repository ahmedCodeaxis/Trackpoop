package com.ahmed.trackpoop.presentation.profile.change_password

data class ChangePasswordState(
    val currentPassword: String = "",
    val currentPasswordError: String? = null,

    val newPassword: String = "",
    val newPasswordError: String? = null,

    val confirmPassword: String = "",
    val confirmPasswordError: String? = null,

    val isLoading: Boolean = false,
    val changePasswordError: String? = null
)
