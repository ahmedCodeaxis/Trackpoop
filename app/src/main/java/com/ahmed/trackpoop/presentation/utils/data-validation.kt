package com.ahmed.trackpoop.utils

import android.util.Patterns

fun validateName(name: String): String? {
    return if (name.isEmpty()) {
        "Name is required"
    } else {
        null
    }
}

fun validateEmail(email: String): String? {
    return if (email.isEmpty()) {
        "Email is required"
    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        "Invalid email address"
    } else {
        null
    }
}

fun validatePassword(password: String): String? {
    return if (password.isEmpty()) {
        "Password is required"
    } else if (password.length < 8) {
        "Password must be at least 8 characters"
    } else {
        null
    }
}

fun validatephone(phone: String): String? {
    return if (phone.isEmpty()) {
        "Phone number is required"
    } else if (!Patterns.PHONE.matcher(phone).matches()) {
        "Invalid phone number"
    } else {
        null
    }
}