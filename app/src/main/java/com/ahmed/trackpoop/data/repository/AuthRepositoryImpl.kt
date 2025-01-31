package com.ahmed.trackpoop.data.repository

import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.AuthApiService
import com.ahmed.trackpoop.data.remote.PoopApiService
import com.ahmed.trackpoop.data.remote.dto.auth.ForgetPasswordDto
import com.ahmed.trackpoop.data.remote.dto.auth.LoginDto
import com.ahmed.trackpoop.data.remote.dto.auth.LoginResponse
import com.ahmed.trackpoop.data.remote.dto.auth.RefreshTokenDto
import com.ahmed.trackpoop.data.remote.dto.auth.RefreshTokenResponse
import com.ahmed.trackpoop.data.remote.dto.auth.ResendOtpDto
import com.ahmed.trackpoop.data.remote.dto.auth.ResetPasswordDto
import com.ahmed.trackpoop.data.remote.dto.user.CreateUserDto
import com.ahmed.trackpoop.data.remote.dto.auth.VerifyOtpDto
import com.ahmed.trackpoop.di.Apiconfig
import com.ahmed.trackpoop.domain.model.User
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.utils.handleException
import com.ahmed.trackpoop.utils.handleHttpException

object AuthRepositoryImpl {
    
    val authApiService= Apiconfig.retrofit.create(AuthApiService::class.java)

     suspend fun signIn(email: String, password: String): Response<LoginResponse?> {
        try {
            val result = authApiService.signIn(LoginDto(email, password))
            return Response(
                success = true,
                data = result,
                message = "Successfully signed in"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun refreshToken(refreshToken: String): Response<RefreshTokenResponse?> {
        try {
            val result = authApiService.refreshToken(RefreshTokenDto(refreshToken))
            return Response(
                success = true,
                data = result,
                message = "Successfully refreshed token"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun forgotPassword(email: String): Response<Any?> {
        try {
            val result = authApiService.forgotPassword(ForgetPasswordDto(email))
            return Response(
                success = true,
                data = result,
                message = "Successfully sent reset password email"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun resetPassword(
        email: String,
        newPassword: String
    ): Response<Any?> {
        try {
            val result = authApiService.resetPassword(
                ResetPasswordDto(
                    email,
                    newPassword
                )
            )
            return Response(
                success = true,
                data = result,
                message = "Successfully reset password"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun signup(user: CreateUserDto,context: Context): Response<User?> {
        try {
            // Create RequestBody objects
            val name = user.name.toRequestBody("text/plain".toMediaTypeOrNull())
            val email = user.email.toRequestBody("text/plain".toMediaTypeOrNull())
            val password = user.password.toRequestBody("text/plain".toMediaTypeOrNull())
            val phone = user.phone.toRequestBody("text/plain".toMediaTypeOrNull())

            // Handle file
            val filePart = user.image?.let { uri ->  // Changed from profilePicture to image
                val stream = context.contentResolver.openInputStream(uri)
                val fileBytes = stream?.readBytes()
                stream?.close()
                
                if (fileBytes != null) {
                    val fileName = getFileName(context, uri) ?: "profile_picture.jpg"
                    val requestFile = fileBytes.toRequestBody(
                        "image/*".toMediaTypeOrNull(),
                        0, fileBytes.size
                    )
                    MultipartBody.Part.createFormData("file", fileName, requestFile)
                } else null
            }

            val result = authApiService.signup(name, email, password, phone, filePart)
            return Response(
                success = true,
                data = result,
                message = "Successfully signed up"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun verifyOtp(
        email: String,
        otp: String
    ): Response<Any?> {
        try {
            val result = authApiService.verifyOtp(VerifyOtpDto(email, otp))
            return Response(
                success = true,
                data = result,
                message = "Successfully verified OTP"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun resendOtp(email: String): Response<Any?> {
        try {
            val result = authApiService.resendOtp(ResendOtpDto(email))
            return Response(
                success = true,
                data = result,
                message = "Successfully resent OTP"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

    private fun getFileName(context: Context, uri: Uri): String? {
        val cursor = context.contentResolver.query(uri, null, null, null, null)
        return cursor?.use {
            val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            it.moveToFirst()
            it.getString(nameIndex)
        }
    }
}