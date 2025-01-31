package com.ahmed.trackpoop.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
import com.ahmed.trackpoop.data.remote.dto.plant.CreatePlantDto
import com.ahmed.trackpoop.data.remote.dto.user.*
import com.ahmed.trackpoop.domain.model.User
import retrofit2.Response
import retrofit2.http.Header

interface UserApiService {


    @GET("user/profile")
    suspend fun getUserProfile(
        @Header("Authorization") token: String,

        ): Response<User>

    @Multipart
    @PATCH("user/update-profile")
    suspend fun editProfile(
        @Header("Authorization") token: String,

        @Part("name") name: RequestBody?,
        @Part("email") email: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part file: MultipartBody.Part?
    ): Response<User>

    @PATCH("user/update-password")
    suspend fun changePassword(
        @Header("Authorization") token: String,

        @Body() updatePassword: UpdatePassword
    ): Response<User>

    @DELETE("delete-profile")
    suspend fun deleteAccount(): Any?

    @PATCH("create-add-plant")
    suspend fun createAndAddPlant(@Body createPlantDto: CreatePlantDto): User

    @PATCH("add-plant/{plantId}")
    suspend fun addPlant(@Path("plantId") plantId: String): User

    @PATCH("remove-plant/{plantId}")
    suspend fun removePlant(@Path("plantId") plantId: String): User

    @POST("my-plants")
    suspend fun addUserPlant(@Body addUserPlantDto: AddUserPlantDto): User

    @PATCH("my-plants/{plantId}/water")
    suspend fun updateWaterReminder(
        @Path("plantId") plantId: String,
        @Body waterReminder: WaterReminderDto
    )

    @PATCH("my-plants/{plantId}/fertilize")
    suspend fun updateFertilizeReminder(
        @Path("plantId") plantId: String,
        @Body fertilizeReminder: FertilizeReminderDto
    ): User

    @DELETE("my-plants/{plantId}")
    suspend fun removeUserPlant(@Path("plantId") plantId: String): User
}