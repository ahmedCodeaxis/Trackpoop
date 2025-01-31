package com.ahmed.trackpoop.presentation.post.post

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.post.PostDto
import com.ahmed.trackpoop.domain.repository.PostRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import java.io.File

class PostsViewModel(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    private val context: Context
) : ViewModel() {

    var state by mutableStateOf(PostsState())
        private set

    init {

        viewModelScope.launch {
            getPosts()

            val user = userRepository.getUserProfile().data
            if (user != null) {
                state = state.copy(user = user, userId = user._id)
            }
        }
    }

    private fun getPosts() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            try {
                val response = postRepository.getPosts()
                state = state.copy(
                    posts = response.data ?: emptyList(),
                    error = null
                )
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun refresh() {
        getPosts()
    }

    fun likePost(postId: String, isLiked: Boolean) {
        viewModelScope.launch {
            try {
                if (isLiked) postRepository.unlikePost(postId)
                else
                    postRepository.likePost(postId)
                getPosts()
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            }
        }
    }

    fun toggleAddPost(show: Boolean) {
        state = state.copy(showAddPost = show)
    }

    fun createPost(content: String, image: Uri?) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                // Call repository to create post
                val result = postRepository.createMyPost(PostDto(
                    content = content,
                    image = image?.let { uri ->
                        val file = File(context.cacheDir, "temp-image")
                        file.writeBytes(context.contentResolver.openInputStream(uri)?.readBytes() ?: byteArrayOf())
                        file
                    }?.toUri()
                ))

                if (result.isSuccessful()) {
                    state = state.copy(postCreated = true)
                    Log.d("PostController", "Post created successfully")
                    toggleAddPost(false)
                    getPosts()
                }
            } catch (e: Exception) {
                // Handle error
                state = state.copy(error = e.message)
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun resetPostCreated() {
        state = state.copy(postCreated = false)
    }
}