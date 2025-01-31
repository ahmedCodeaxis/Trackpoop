package com.ahmed.trackpoop.domain.repository

import android.content.Context
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.dto.plant.CreatePlantDto
import com.ahmed.trackpoop.data.remote.dto.user.*
import com.ahmed.trackpoop.domain.model.User

interface UserRepository {
    suspend fun getUserProfile(): Response<User?>
    suspend fun editProfile(updateUserDto: UpdateUserDto,context: Context): Response<User?>
    suspend fun changePassword(currentPassword: String, newPassword: String): Response<User?>
    suspend fun deleteAccount(): Response<Any?>
    suspend fun createAndAddPlant(createPlantDto: CreatePlantDto): Response<User?>
    suspend fun addPlant(plantId: String): Response<User?>
    suspend fun removePlant(plantId: String): Response<User?>
    suspend fun addUserPlant(addUserPlantDto: AddUserPlantDto): Response<User?>
    suspend fun updateWaterReminder(plantId: String, waterReminder: WaterReminderDto): Response<User?>
    suspend fun updateFertilizeReminder(plantId: String, fertilizeReminder: FertilizeReminderDto): Response<User?>
    suspend fun removeUserPlant(plantId: String): Response<User?>
}