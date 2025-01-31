package com.ahmed.trackpoop.utils

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException
import com.ahmed.trackpoop.core.models.ErrorResponse
import com.ahmed.trackpoop.core.models.Response

inline fun <reified T> handleHttpException(e: HttpException): Response<T?> {
    val errorResponse = e.response()?.errorBody()?.string()
    val errorMessage = if (errorResponse != null) {
        Gson().fromJson(errorResponse, ErrorResponse::class.java).message
    } else {
        e.message()
    }
    Log.e("handleHttpException", "handleHttpException: $errorMessage")
    return Response(
        success = false,
        message = errorMessage ?: "An error occurred"
    )
}

inline fun <reified T> handleException(e: Exception): Response<T?> {
    Log.e("handleException", "handleException: ${e.message}")
    return Response(
        success = false,
        message = e.message ?: "An error occurred"
    )
}