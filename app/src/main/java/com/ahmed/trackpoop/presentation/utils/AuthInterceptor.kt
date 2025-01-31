package com.ahmed.trackpoop.utils

import okhttp3.Interceptor
import okhttp3.Response
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import kotlinx.coroutines.runBlocking

class AuthInterceptor(private val preferencesManager: PreferencesManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { preferencesManager.getUserToken() } // Fetch token synchronously
        val requestBuilder = chain.request().newBuilder()
        if (token != null) {
            requestBuilder.addHeader("Authorization", "Bearer $token")
        }

        return chain.proceed(requestBuilder.build())
    }

}
