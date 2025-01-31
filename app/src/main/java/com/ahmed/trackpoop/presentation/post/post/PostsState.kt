package com.ahmed.trackpoop.presentation.post.post

import com.ahmed.trackpoop.domain.model.Post
import com.ahmed.trackpoop.domain.model.User

data class PostsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val posts: List<Post> = emptyList(),
    val user: User? = null,
    val userId: String? = null,
    val showAddPost: Boolean = false,
    val postCreated: Boolean = false
)