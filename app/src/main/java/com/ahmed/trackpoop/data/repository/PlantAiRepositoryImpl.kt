package com.ahmed.trackpoop.data.repository

import android.util.Log
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import com.ahmed.trackpoop.data.remote.PlantAiApiService
import com.ahmed.trackpoop.data.remote.dto.plant.PlantAiResponse
import com.ahmed.trackpoop.data.remote.dto.plant.Suggestion
import com.ahmed.trackpoop.domain.repository.PlantAiRepository
import java.io.File
import kotlin.Result

class PlantAiRepositoryImpl(private val plantAiApiService: PlantAiApiService) : PlantAiRepository {

    override suspend fun identifyPlantImage(
        imageFile: File,
        callback: (Result<PlantAiResponse>) -> Unit
    ) {
        val requestBody =
            MultipartBody.Part.createFormData("images", imageFile.name, imageFile.asRequestBody())

        try {
            plantAiApiService.identifyPlant(requestBody)
                .enqueue(object : Callback<PlantAiResponse> {
                    override fun onResponse(
                        call: Call<PlantAiResponse>,
                        response: Response<PlantAiResponse>
                    ) {
                        if (response.isSuccessful) {
                            val plantResponse = response.body()
                            val suggestions = plantResponse?.result?.classification?.suggestions
                            if (!suggestions.isNullOrEmpty()) {                              
                                callback(Result.success(plantResponse))
                            } else {
                                callback(Result.failure(Throwable("No plant suggestions found")))
                            }
                        } else {
                            Log.e(
                                "PlantID",
                                "Error: ${response.message()} - ${response.errorBody()?.string()}"
                            )
                            callback(Result.failure(Throwable("Error: ${response.message()}")))
                        }
                    }

                    override fun onFailure(call: Call<PlantAiResponse>, t: Throwable) {
                        Log.e("PlantID", "Request failed: ${t.localizedMessage}")
                        callback(Result.failure(t))
                    }
                })
        } catch (e: HttpException) {
            Log.e("PlantID", "HttpException: ${e.message()}")
            callback(Result.failure(Throwable("HttpException: ${e.message()}")))
        } catch (e: Exception) {
            Log.e("PlantID", "Exception: ${e.message}")
            callback(Result.failure(Throwable("Internal Error")))
        }
    }

    override suspend fun healthPlantImage(
        imageFile: File,
        callback: (Result<List<Suggestion>>) -> Unit
    ) {
        val requestBody =
            MultipartBody.Part.createFormData("images", imageFile.name, imageFile.asRequestBody())

        plantAiApiService.healthPlant(requestBody)
            .enqueue(object : Callback<PlantAiResponse> {
                override fun onResponse(
                    call: Call<PlantAiResponse>,
                    response: Response<PlantAiResponse>
                ) {
                    if (response.isSuccessful) {
                        val plantResponse = response.body()
                        val suggestions = plantResponse?.result?.disease?.suggestions
                        if (!suggestions.isNullOrEmpty()) {
                            callback(Result.success(suggestions))
                        } else {
                            callback(Result.failure(Throwable("No plant suggestions found")))
                        }
                    } else {
                        Log.e(
                            "PlantID",
                            "Error: ${response.message()} - ${response.errorBody()?.string()}"
                        )
                        callback(Result.failure(Throwable("Error: ${response.message()}")))
                    }
                }

                override fun onFailure(call: Call<PlantAiResponse>, t: Throwable) {
                    Log.e("PlantID", "Request failed: ${t.localizedMessage}")
                    callback(Result.failure(t))
                }
            })
    }

}