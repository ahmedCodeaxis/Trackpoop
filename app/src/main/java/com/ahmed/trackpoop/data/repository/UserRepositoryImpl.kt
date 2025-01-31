package com.ahmed.trackpoop.data.repository

import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.provider.OpenableColumns
import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.data.remote.dto.plant.CreatePlantDto
import com.ahmed.trackpoop.data.remote.dto.user.AddUserPlantDto
import com.ahmed.trackpoop.data.remote.dto.user.FertilizeReminderDto
import com.ahmed.trackpoop.data.remote.dto.user.UpdatePassword
import com.ahmed.trackpoop.data.remote.dto.user.UpdateUserDto
import com.ahmed.trackpoop.data.remote.dto.user.WaterReminderDto
import com.ahmed.trackpoop.di.Apiconfig
import com.ahmed.trackpoop.domain.model.User
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.utils.handleException
import com.ahmed.trackpoop.utils.handleHttpException

object UserRepositoryImpl {

    private val userApiService = Apiconfig.retrofit.create(UserApiService::class.java)

    suspend fun getUserProfile(token: String): Response<User?> {
        if (token.isEmpty()) {
            throw Exception("Token is empty")
        }
        return try {
            Log.e("token is :: ", token)
            val result = userApiService.getUserProfile("Bearer $token")
            Response(
                success = true,
                data = result.body(),
                message = "Successfully fetched user profile"
            )
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun editProfile(updateUserDto: UpdateUserDto, context: Context, token: String): Response<User?> {
        return try {
            val name = updateUserDto.name?.toRequestBody("text/plain".toMediaTypeOrNull())
            val phone = updateUserDto.phone?.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = updateUserDto.email?.toRequestBody("text/plain".toMediaTypeOrNull())

            val filePart = updateUserDto.image?.let { uri ->
                context.contentResolver.openInputStream(uri)?.use { stream ->
                    val fileBytes = stream.readBytes()
                    val fileName = getFileName(context, uri) ?: "profile_picture.jpg"
                    val requestFile = fileBytes.toRequestBody("image/*".toMediaTypeOrNull(), 0, fileBytes.size)
                    MultipartBody.Part.createFormData("file", fileName, requestFile)
                }
            }

            val response = userApiService.editProfile(
                token = "Bearer $token",
                name = name,
                email = email,
                phone = phone,
                file = filePart
            )

            if (response.isSuccessful) {
                Response(success = true, data = response.body(), message = "Successfully updated profile")
            } else {
                Response(success = false, data = null, message = "Failed to update profile: ${response.message()}")
            }
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        }
    }

    suspend fun changePassword(currentPassword: String, newPassword: String, token: String, context: Context): Response<User?> {
        if (token.isEmpty()) {
            throw Exception("Token is empty or null")
        }

        return try {
            Log.e("ChangePassword", "Received token: $token")

            val result = userApiService.changePassword(
                updatePassword = UpdatePassword(currentPassword, newPassword),
                token = "Bearer $token"
            )

            Log.e("ChangePassword", "Token used for API: Bearer $token")

            if (result.isSuccessful) {
                Response(success = true, data = result.body(), message = "Successfully changed password")
            } else {
                Log.e("ChangePassword Error", "Failed with response: ${result.message()}")
                Response(success = false, data = null, message = "Failed to change password: ${result.message()}")
            }
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }


    suspend fun deleteAccount(): Response<Any?> {
        return try {
            userApiService.deleteAccount()
            Response(success = true, data = null, message = "Successfully deleted account")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun createAndAddPlant(createPlantDto: CreatePlantDto): Response<User?> {
        return try {
            val result = userApiService.createAndAddPlant(createPlantDto)
            Response(success = true, data = result, message = "Successfully created and added plant")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun addPlant(plantId: String): Response<User?> {
        return try {
            val result = userApiService.addPlant(plantId)
            Response(success = true, data = result, message = "Successfully added plant")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun removePlant(plantId: String): Response<User?> {
        return try {
            val result = userApiService.removePlant(plantId)
            Response(success = true, data = result, message = "Successfully removed plant")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun addUserPlant(addUserPlantDto: AddUserPlantDto): Response<User?> {
        return try {
            val result = userApiService.addUserPlant(addUserPlantDto)
            Response(success = true, data = result, message = "Successfully added user plant")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun updateWaterReminder(plantId: String, waterReminder: WaterReminderDto): Response<User?> {
        return try {
            userApiService.updateWaterReminder(plantId, waterReminder)
            Response(success = true, message = "Successfully updated water reminder")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun updateFertilizeReminder(plantId: String, fertilizeReminder: FertilizeReminderDto): Response<User?> {
        return try {
            val result = userApiService.updateFertilizeReminder(plantId, fertilizeReminder)
            Response(success = true, data = result, message = "Successfully updated fertilize reminder")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    suspend fun removeUserPlant(plantId: String): Response<User?> {
        return try {
            val result = userApiService.removeUserPlant(plantId)
            Response(success = true, data = result, message = "Successfully removed user plant")
        } catch (e: HttpException) {
            handleHttpException(e)
        } catch (e: Exception) {
            handleException(e)
        }
    }
}
