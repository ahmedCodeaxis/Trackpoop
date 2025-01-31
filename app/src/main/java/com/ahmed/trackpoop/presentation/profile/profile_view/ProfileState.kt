package com.ahmed.trackpoop.presentation.profile.profile_view

import com.ahmed.trackpoop.domain.model.Post
import com.ahmed.trackpoop.domain.model.User
import com.ahmed.trackpoop.domain.model.emptyUser

data class ProfileState(
    val user: User = emptyUser,
    val posts: List<Post> = emptyList(),
    val isLoading: Boolean = false,
    val bio: String = "",
    val location: String = "",
    val profileImage: String = ""
)
