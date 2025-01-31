package com.ahmed.trackpoop.data.remote.dto.poop

data class PoopDto(
    val color: String,
    val type: String,
    val date: String
)

data class PoopResponse(
    val color: String,
    val type: String,
    val date: String
)