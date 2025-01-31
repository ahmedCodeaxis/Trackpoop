package com.ahmed.trackpoop.presentation.auth.sign_in

data class SignInState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val emailError: String? = null,
    val passwordError: String? = null,
    val signInError: String? = null,

    val isLoggedIn: Boolean = false
)
