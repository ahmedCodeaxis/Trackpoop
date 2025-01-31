package com.ahmed.trackpoop.domain.model

data class Post(
    val _id: String,
    val content: String,
    val image: String,
    val isArchived: Boolean,
    val user: User,
    val comments: List<Comment>,
    val likes: List<String>,
    val dislikes: List<String>,
    val createdAt: String,
    val updatedAt: String,
)

data class Comment(
    val content: String,
    val user: UserComment,
)
