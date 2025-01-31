package com.ahmed.trackpoop.data.repository

import android.util.Log
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.PostApiService
import com.ahmed.trackpoop.data.remote.dto.post.CommentDto
import com.ahmed.trackpoop.data.remote.dto.post.PostDto
import com.ahmed.trackpoop.domain.model.Post
import com.ahmed.trackpoop.domain.repository.PostRepository
import com.ahmed.trackpoop.utils.handleException
import com.ahmed.trackpoop.utils.handleHttpException
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.di.Apiconfig

object PostRepositoryImpl  {
    val postApiService= Apiconfig.retrofit.create(PostApiService::class.java)

     suspend fun getPosts(): Response<List<Post>?> {
        try {
            val result = postApiService.getPosts()
            return Response(
                success = true,
                data = result,
                message = "Successfully fetched posts"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun getPostById(postId: String): Response<Post?> {
        try {
            val result = postApiService.getPostById(postId)
            return Response(
                success = true,
                data = result,
                message = "Successfully fetched post"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun createMyPost(postDto: PostDto,context: Context): Response<Post?> {
        try {
            // Log the raw input data
            Log.d("PostRepositoryImpl", "User Input - Content: ${postDto.content}, Image URI: ${postDto.image}")

            // Prepare the content part
            val content = postDto.content?.toRequestBody("text/plain".toMediaTypeOrNull())
            Log.d("PostRepositoryImpl", "Prepared Content: $content")

            // Prepare the file part
            val filePart = postDto.image?.let { uri ->
                try {
                    val stream = context.contentResolver.openInputStream(uri)
                    val fileBytes = stream?.readBytes()
                    stream?.close()

                    if (fileBytes != null) {
                        val fileName = getFileName(context, uri) ?: "post_image.jpg"
                        Log.d("PostRepositoryImpl", "File Name: $fileName, File Size: ${fileBytes.size} bytes")
                        val requestFile = fileBytes.toRequestBody(
                            "image/*".toMediaTypeOrNull(),
                            0, fileBytes.size
                        )
                        MultipartBody.Part.createFormData("file", fileName, requestFile)
                    } else {
                        Log.e("PostRepositoryImpl", "Failed to read file bytes from URI: $uri")
                        null
                    }
                } catch (e: Exception) {
                    Log.e("PostRepositoryImpl", "Error reading image file: ${e.message}", e)
                    null
                }
            }

            Log.d("PostRepositoryImpl", "File Part: $filePart")

            // Make the API call
            val result = content?.let { postApiService.createMyPost(it, filePart) }
            Log.d("PostRepositoryImpl", "API Call Result: $result")

            return Response(
                success = true,
                data = result,
                message = "Post created successfully"
            )
        } catch (e: HttpException) {
            Log.e("PostRepositoryImpl", "HTTP Exception: ${e.message()}", e)
            return handleHttpException(e)
        } catch (e: Exception) {
            Log.e("PostRepositoryImpl", "General Exception: ${e.message}", e)
            return handleException(e)
        }
    }

     suspend fun getMyPosts(): Response<List<Post>?> {
        try {
            val result = postApiService.getMyPosts()
            return Response(
                success = true,
                data = result,
                message = "Successfully fetched posts"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun likePost(postId: String): Response<Unit?> {
        try {
            postApiService.likePost(postId)
            return Response(
                success = true,
                data = null,
                message = "Successfully liked post"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun unlikePost(postId: String): Response<Unit?> {
        try {
            postApiService.unlikePost(postId)
            return Response(
                success = true,
                data = null,
                message = "Successfully unliked post"
            )
        } catch (e: HttpException) {
            return handleHttpException(e)
        } catch (e: Exception) {
            return handleException(e)
        }
    }

     suspend fun addComment(postId: String, comment: CommentDto): Response<Unit?> {
        try {
            postApiService.addComment(postId, comment)
            return Response(
                success = true,
                data = null,
                message = "Successfully added comment"
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