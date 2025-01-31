package com.ahmed.trackpoop.data.remote.dto.plant

data class PlantAiResponse(
    val access_token: String?,
    val model_version: String?,
    val custom_id: String?,
    val input: Input?,
    val result: Result?,
    val status: String?,
    val sla_compliant_client: Boolean?,
    val sla_compliant_system: Boolean?,
    val created: Double?,
    val completed: Double?
)

data class Input(
    val latitude: Double?,
    val longitude: Double?,
    val similar_images: Boolean?,
    val health: String?, // Added for requests that include health
    val images: List<String>?,
    val datetime: String?
)

data class Result(
    val is_plant: IsPlant?,
    val is_healthy: IsHealthy?, // Added for requests that include health information
    val classification: Classification?, // Included for classification results
    val disease: Disease? // Included for disease suggestions
)

data class IsPlant(
    val probability: Double?,
    val binary: Boolean?,
    val threshold: Double?
)

data class IsHealthy(
    val binary: Boolean?, // Nullable to handle cases where it's not present
    val threshold: Double?,
    val probability: Double?
)

data class Classification(
    val suggestions: List<Suggestion>? // Classification suggestions, optional
)

data class Disease(
    val suggestions: List<Suggestion>? // Disease suggestions, optional
)

data class Suggestion(
    val id: String?,
    val name: String?,
    val probability: Double?,
    val similar_images: List<SimilarImage>?,
    val details: Details?
)

data class SimilarImage(
    val id: String?,
    val url: String?,
    val similarity: Double?,
    val url_small: String?,
    val license_name: String?, // License-related fields are optional
    val license_url: String?,
    val citation: String?
)

data class Details(
    val language: String?,
    val entity_id: String?
)