package com.ahmed.trackpoop.domain.model

data class Chat(
    val _id: String,
    val participants: List<User>,
    val messages: List<Message>,
)

data class Message(
    val _id: String,
    val sender: User,
    val content: String,
    val createdAt: String,
    val updatedAt: String,
)