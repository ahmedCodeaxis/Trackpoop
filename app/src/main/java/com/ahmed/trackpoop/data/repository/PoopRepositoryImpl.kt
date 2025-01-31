package com.ahmed.trackpoop.data.repository

import android.content.Context
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.PoopApiService
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.di.Apiconfig
import com.ahmed.trackpoop.domain.model.Poop
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.utils.handleException
import com.ahmed.trackpoop.utils.handleHttpException
import retrofit2.HttpException

object PoopRepositoryImpl  {
    val poopApiService= Apiconfig.retrofit.create(PoopApiService::class.java)

     suspend fun getPoop(token: String): Response<List<Poop>?> {
        return try {
            val result = poopApiService.getPoop("Bearer $token") // Correction de l'appel à l'API
            Response(
                success = true,
                data = result,
                message = "Successfully fetched poop"
            )
        } catch (e: HttpException) {
            handleHttpException(e) // Gestion des exceptions HTTP
        } catch (e: Exception) {
            handleException(e) // Gestion des autres exceptions
        }
    }
}
