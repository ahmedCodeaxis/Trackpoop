package com.ahmed.trackpoop.presentation.profile.profile_settings

import com.ahmed.trackpoop.domain.model.Badge

data class ProfileSettingsState (
    val name: String = "",
    val email: String = "",
    val badge: Badge = Badge.BEGINNER,
    val image: String? = "",

    val emailError: String = "",
    val nameError: String = "",
    val passwordError: String = "",
    val newPasswordError: String = "",

    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false

)