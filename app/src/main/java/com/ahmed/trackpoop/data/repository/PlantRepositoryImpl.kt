package com.ahmed.trackpoop.data.repository

import android.util.Log
import retrofit2.HttpException
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.PlantApiService
import com.ahmed.trackpoop.domain.model.Plant
import com.ahmed.trackpoop.domain.repository.PlantRepository
import com.ahmed.trackpoop.utils.handleException
import com.ahmed.trackpoop.utils.handleHttpException

class PlantRepositoryImpl(private val plantApiService: PlantApiService) : PlantRepository {
    override suspend fun getPlants(): Response<List<Plant>?> {
        try {
            val result = plantApiService.getPlants()
            return Response(
                success = true,
                data = result,
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

    override suspend fun getPlantById(plantId: Int): Response<Plant?> {
        try {
            val result = plantApiService.getPlantById(plantId)
            return Response(
                success = true,
                data = result,
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

    override suspend fun createPlant(plant: Plant): Response<Plant?> {
        try {
            val result = plantApiService.createPlant(plant)
            return Response(
                success = true,
                data = result,
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

    override suspend fun updatePlant(plantId: String, plant: Plant): Response<Plant?> {
        try {
            val result = plantApiService.updatePlant(plantId, plant)
            return Response(
                success = true,
                data = result,
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

    override suspend fun deletePlant(plantId: Int): Response<Any?> {
        try {
            val result = plantApiService.deletePlant(plantId)
            return Response(
                success = true,
                data = result,
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }
}