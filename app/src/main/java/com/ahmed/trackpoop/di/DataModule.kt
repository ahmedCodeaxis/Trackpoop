package com.ahmed.trackpoop.di

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.remote.AuthApiService
import com.ahmed.trackpoop.data.remote.PlantAiApiService
import com.ahmed.trackpoop.data.remote.PlantApiService
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.data.repository.AuthRepositoryImpl
import com.ahmed.trackpoop.data.repository.PlantAiRepositoryImpl
import com.ahmed.trackpoop.data.repository.PlantRepositoryImpl
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.domain.repository.PlantAiRepository
import com.ahmed.trackpoop.domain.repository.PlantRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.utils.AuthInterceptor
import com.ahmed.trackpoop.utils.PlantAiInterceptor
import org.koin.core.qualifier.named
import com.ahmed.trackpoop.data.remote.PostApiService
import com.ahmed.trackpoop.data.repository.PoopRepositoryImpl
import com.ahmed.trackpoop.data.repository.PostRepositoryImpl
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.domain.repository.PostRepository
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

const val SERVER_URL = "http://192.168.1.143:3000"
const val BASE_URL = "http://192.168.1.143:3000/api/v1/"
const val API_URL = "https://plant.id/api/v3/"

val DataModule = module {

    single<SharedPreferences> {
        androidContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    }

    single { PreferencesManager(get()) }

    single(named("AuthClient")) {
        val preferencesManager: PreferencesManager = get()
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(preferencesManager))
            .build()
    }

    single(named("PlantAiClient")) {
        OkHttpClient.Builder()
            .addInterceptor(PlantAiInterceptor("uqsfoMlAIW67CP0ips8DgKocw4WpkwVaG12lCkE6WDBcuwv6JQ"))
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Add Gson singleton
    single { 
        GsonBuilder()
            .registerTypeAdapter(LocalDate::class.java, LocalDateSerializer())
            .registerTypeAdapter(LocalDate::class.java, LocalDateDeserializer())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeSerializer())
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeDeserializer())
            .create() 
    }

    // Update Retrofit for AuthApiService
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(BASE_URL )
            .client(get(named("AuthClient")))
            .build()
            .create(AuthApiService::class.java)
    }

    // Update Retrofit for UserApiService
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(BASE_URL)
            .client(get(named("AuthClient"))) // Same AuthClient if applicable
            .build()
            .create(UserApiService::class.java)
    }

    // Update Retrofit for PlantApiService
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(BASE_URL + PlantApiService.BASE_URL)
            .client(get(named("AuthClient"))) // Same AuthClient if applicable
            .build()
            .create(PlantApiService::class.java)
    }

    // Update Retrofit for PostApiService
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(BASE_URL + PostApiService.BASE_URL)
            .client(get(named("AuthClient"))) // Same AuthClient if applicable
            .build()
            .create(PostApiService::class.java)
    }

    // Update Retrofit for PlantAiApiService
    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .baseUrl(API_URL)
            .client(get(named("PlantAiClient")))
            .build()
            .create(PlantAiApiService::class.java)
    }

    // Repositories
    //single<AuthRepository> { AuthRepositoryImpl(get(), get()) }
    //single<UserRepository> { UserRepositoryImpl(get(), get()) }
    single<PlantRepository> { PlantRepositoryImpl(get()) }
    single<PlantAiRepository> { PlantAiRepositoryImpl(get()) }
   // single<PostRepository> { PostRepositoryImpl(get(), get()) }
   // factory<PoopRepository> { PoopRepositoryImpl } // Use factory for object classes
    //factory<UserRepository> { UserRepositoryImpl }

}

class LocalDateSerializer : JsonSerializer<LocalDate> {
    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

class LocalDateDeserializer : JsonDeserializer<LocalDate> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
        val dateString = json?.asString
        return when {
            dateString == null -> throw IllegalArgumentException("Date string cannot be null")
            dateString.contains('T') -> LocalDate.parse(dateString.substring(0, 10))
            else -> LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE)
        }
    }
}

class LocalDateTimeSerializer : JsonSerializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}

class LocalDateTimeDeserializer : JsonDeserializer<LocalDateTime> {
    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        val dateString = json?.asString ?: throw IllegalArgumentException("DateTime string cannot be null")
        return LocalDateTime.parse(dateString, DateTimeFormatter.ISO_DATE_TIME)
    }
}