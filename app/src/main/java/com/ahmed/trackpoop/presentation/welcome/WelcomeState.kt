package com.ahmed.trackpoop.presentation.welcome

import com.ahmed.trackpoop.domain.model.User

data class WelcomeState(
    val isLoggedIn: Boolean = false,
    val user: User? = null
)