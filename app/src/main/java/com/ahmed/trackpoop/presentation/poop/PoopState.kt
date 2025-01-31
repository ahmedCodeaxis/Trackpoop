package com.ahmed.trackpoop.presentation.poop

import com.ahmed.trackpoop.domain.model.Poop
import com.ahmed.trackpoop.domain.model.User

// Make PoopState a data class
data class PoopState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val poop: List<Poop> = emptyList(),
    val user: User? = null,
    val userId: String? = null,
    val showAddPost: Boolean = false,
    val postCreated: Boolean = false
)
