package com.ahmed.trackpoop.presentation.post.post_details

import com.ahmed.trackpoop.domain.model.Post

data class PostDetailsState (
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val post: Post? = null,
    val isLiked: Boolean = false,

    val comment: String = "",

)