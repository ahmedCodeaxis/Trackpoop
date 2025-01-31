package com.ahmed.trackpoop.data.remote.dto.post

import android.net.Uri

data class PostDto(
    val content: String?,
    val image: Uri? = null
)

data class PostResponse(
    val id: String,
    val content: String,
    val imageUrl: String?,
    val author: String,
    val likes: Int,
    val comments: List<CommentDto>,
    val createdAt: String,
    val updatedAt: String
)

data class CommentDto(
    val content: String,
)