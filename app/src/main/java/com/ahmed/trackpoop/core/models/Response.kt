package com.ahmed.trackpoop.core.models

import com.google.gson.annotations.SerializedName

data class Response<T> (
    val success: Boolean = false,
    @SerializedName("message")
    val message: String = "",
    @SerializedName("data")
    val data: T? = null
){
    fun isSuccessful() = success
}

data class ErrorResponse(
    @SerializedName("message")
    val message: String? = "",
    @SerializedName("error")
    val error: String? = "",
    @SerializedName("status")
    val status: Int? = 0
)