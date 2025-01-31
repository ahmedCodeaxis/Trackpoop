package com.ahmed.trackpoop.domain.repository

import com.ahmed.trackpoop.core.models.Response
import com.ahmed.trackpoop.data.remote.dto.post.CommentDto
import com.ahmed.trackpoop.data.remote.dto.post.PostDto
import com.ahmed.trackpoop.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): Response<List<Post>?>
    suspend fun getPostById(postId: String): Response<Post?>
    suspend fun createMyPost(post: PostDto): Response<Post?>
    suspend fun getMyPosts(): Response<List<Post>?>
    suspend fun likePost(postId: String): Response<Unit?>
    suspend fun unlikePost(postId: String): Response<Unit?>
    suspend fun addComment(postId: String, comment: CommentDto): Response<Unit?>
}