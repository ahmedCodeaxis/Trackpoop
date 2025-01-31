package com.ahmed.trackpoop.data.remote

import com.ahmed.trackpoop.core.models.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Multipart
import retrofit2.http.Part
import okhttp3.MultipartBody
import okhttp3.RequestBody
import com.ahmed.trackpoop.data.remote.dto.post.CommentDto
import com.ahmed.trackpoop.data.remote.dto.post.PostDto
import com.ahmed.trackpoop.domain.model.Comment
import com.ahmed.trackpoop.domain.model.Post

interface PostApiService {
    companion object {
        const val BASE_URL = "post/"
    }

    @GET("?type=notArchived")
    suspend fun getPosts(): List<Post>

    @GET("{postId}")
    suspend fun getPostById(@Path("postId") postId: String): Post

    @Multipart
    @POST("create")
    suspend fun createMyPost(
        @Part("content") content: RequestBody,
        @Part file: MultipartBody.Part?
    ): Post

    @GET("my")
    suspend fun getMyPosts(): List<Post>

    @PATCH("{postId}/like")
    suspend fun likePost(@Path("postId") postId: String)

    @PATCH("{postId}/unlike")
    suspend fun unlikePost(@Path("postId") postId: String)

    @PATCH("{postId}/comment")
    suspend fun addComment(@Path("postId") postId: String, @Body() comment: CommentDto)
}