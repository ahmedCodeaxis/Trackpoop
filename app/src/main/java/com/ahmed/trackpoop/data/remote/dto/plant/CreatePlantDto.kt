package com.ahmed.trackpoop.data.remote.dto.plant

data class CreatePlantDto(
    val name: String,
    val description: String,
    val image: String,
    val isPublished: Boolean? = false,
)

data class UpdatePlantDto (
    val name: String?,
    val description: String?,
    val image: String?,
    val isPublished: Boolean?,
)