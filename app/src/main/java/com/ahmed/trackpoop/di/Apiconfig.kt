package com.ahmed.trackpoop.di

import android.content.Context
import android.content.SharedPreferences
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.remote.AuthApiService
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.utils.AuthInterceptor
import com.ahmed.trackpoop.utils.PlantAiInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


object Apiconfig {
    const val SERVER_URL = "http://192.168.1.143:3000"
    const val BASE_URL = "http://192.168.1.143:3000/api/v1/"
    const val API_URL = "https://plant.id/api/v3/"
   // val sharedPreferences: SharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
   // val preferencesManager = PreferencesManager(sharedPreferences)

    // Interceptors for API calls
  //  val authInterceptor = AuthInterceptor(preferencesManager)
    val httpclient= OkHttpClient.Builder()
        .addInterceptor(PlantAiInterceptor("uqsfoMlAIW67CP0ips8DgKocw4WpkwVaG12lCkE6WDBcuwv6JQ"))
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
    .build()

    val gson = GsonBuilder()
    .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
    .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
    .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
    .create()
    val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL )
        .client(httpclient)
        .build()
    val poopretrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL+"poop/")
        .client(httpclient)
        .build()
    val useretrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BASE_URL)
        .client(httpclient)
        .build()

    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)


}