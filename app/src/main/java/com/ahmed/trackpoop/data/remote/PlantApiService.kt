package com.ahmed.trackpoop.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import com.ahmed.trackpoop.domain.model.Plant

interface PlantApiService {
    companion object {
        const val BASE_URL = "plant/"
    }

    @GET("published")
    suspend fun getPlants(): List<Plant>

    @GET("{plantId}")
    suspend fun getPlantById(plantId: Int): Plant

    @POST()
    suspend fun createPlant(@Body() plant: Plant): Plant

    @PATCH("{plantId}")
    suspend fun updatePlant( @Path("plantId") plantId: String, @Body() plant: Plant): Plant

    @DELETE("{plantId}")
    suspend fun deletePlant(plantId: Int)
}