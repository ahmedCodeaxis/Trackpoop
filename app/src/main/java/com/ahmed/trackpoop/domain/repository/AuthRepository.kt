package com.ahmed.trackpoop.domain.repository

import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.dto.auth.LoginResponse
import com.ahmed.trackpoop.data.remote.dto.auth.RefreshTokenResponse
import com.ahmed.trackpoop.data.remote.dto.user.CreateUserDto
import com.ahmed.trackpoop.domain.model.User

interface AuthRepository {

    suspend fun signIn(email: String, password: String): Response<LoginResponse?>
    suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse?>
    suspend fun forgotPassword(email: String): Response<Any?>
    suspend fun resetPassword(email: String, newPassword: String): Response<Any?>
    suspend fun signup(user: CreateUserDto): Response<User?>
    suspend fun verifyOtp(email: String, otp: String): Response<Any?>
    suspend fun resendOtp(email: String): Response<Any?>

}