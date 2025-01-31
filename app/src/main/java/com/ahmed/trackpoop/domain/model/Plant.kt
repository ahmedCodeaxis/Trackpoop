package com.ahmed.trackpoop.domain.model


data class Plant(
    val _id: String,
    val name: String,
    val description: String,
    val scientificName: String,
    val family: String,
    val image: String,
    val isPublished: Boolean,
    val createdAt: String,
    val updatedAt: String,
)