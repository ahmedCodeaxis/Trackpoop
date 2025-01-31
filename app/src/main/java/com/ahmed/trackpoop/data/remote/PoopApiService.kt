package com.ahmed.trackpoop.data.remote

import com.ahmed.trackpoop.domain.model.Poop
import retrofit2.http.GET
import retrofit2.http.Header

interface PoopApiService {


    @GET("poop/")

    suspend fun getPoop(
        @Header("Authorization") token: String,

        ): List<Poop>
}