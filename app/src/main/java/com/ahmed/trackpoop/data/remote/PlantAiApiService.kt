package com.ahmed.trackpoop.data.remote

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import com.ahmed.trackpoop.data.remote.dto.plant.PlantAiResponse

interface PlantAiApiService {
    @Multipart
    @POST("identification")
    fun identifyPlant(
        @Part image: MultipartBody.Part
    ): Call<PlantAiResponse>

    @Multipart
    @POST("health_assessment")
    fun healthPlant(
        @Part image: MultipartBody.Part
    ): Call<PlantAiResponse>
}