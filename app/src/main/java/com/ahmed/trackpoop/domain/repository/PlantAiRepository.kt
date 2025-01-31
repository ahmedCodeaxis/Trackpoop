package com.ahmed.trackpoop.domain.repository

import com.ahmed.trackpoop.data.remote.dto.plant.PlantAiResponse
import com.ahmed.trackpoop.data.remote.dto.plant.Suggestion
import java.io.File

interface PlantAiRepository {
    suspend fun identifyPlantImage(
        imageFile: File,
        callback: (Result<PlantAiResponse>) -> Unit
    )

    suspend fun healthPlantImage(
        imageFile: File,
        callback: (Result<List<Suggestion>>) -> Unit
    )
}
