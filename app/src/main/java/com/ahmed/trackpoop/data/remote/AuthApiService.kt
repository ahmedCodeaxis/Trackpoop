package com.ahmed.trackpoop.data.remote

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Multipart
import retrofit2.http.Part
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.ahmed.trackpoop.data.remote.dto.auth.ForgetPasswordDto
import com.ahmed.trackpoop.data.remote.dto.auth.LoginDto
import com.ahmed.trackpoop.data.remote.dto.auth.LoginResponse
import com.ahmed.trackpoop.data.remote.dto.auth.RefreshTokenDto
import com.ahmed.trackpoop.data.remote.dto.auth.RefreshTokenResponse
import com.ahmed.trackpoop.data.remote.dto.auth.ResendOtpDto
import com.ahmed.trackpoop.data.remote.dto.auth.ResetPasswordDto
import com.ahmed.trackpoop.data.remote.dto.user.CreateUserDto
import com.ahmed.trackpoop.data.remote.dto.auth.VerifyOtpDto
import com.ahmed.trackpoop.domain.model.User

interface AuthApiService {


    @POST("auth/sign-in")
    suspend fun signIn(@Body credentials: LoginDto): LoginResponse

    @POST("auth/refresh-token")
    suspend fun refreshToken(@Body credentials: RefreshTokenDto): RefreshTokenResponse

    @POST("auth/forget-password")
    suspend fun forgotPassword(@Body credentials: ForgetPasswordDto)

    @POST("auth/reset-password")
    suspend fun resetPassword(@Body credentials: ResetPasswordDto)

    @Multipart
    @POST("auth/sign-up")
    suspend fun signup(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part file: MultipartBody.Part?
    ): User

    @POST("verify-otp")
    suspend fun verifyOtp(@Body otpDto: VerifyOtpDto)

    @POST("resend-otp")
    suspend fun resendOtp(@Body otpDto: ResendOtpDto)
}