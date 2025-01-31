package com.ahmed.trackpoop.domain.repository

import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.domain.model.Plant

interface PlantRepository {

    suspend fun getPlants(): Response<List<Plant>?>
    suspend fun getPlantById(plantId: Int): Response<Plant?>
    suspend fun createPlant(plant: Plant): Response<Plant?>
    suspend fun updatePlant(plantId: String, plant: Plant): Response<Plant?>
    suspend fun deletePlant(plantId: Int): Response<Any?>

}