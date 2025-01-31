package com.ahmed.trackpoop.utils

import okhttp3.Interceptor
import okhttp3.Response

class PlantAiInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("Api-Key", apiKey)
            .build()
        return chain.proceed(request)
    }
}