package com.ahmed.trackpoop.presentation.post.post_details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.post.CommentDto
import com.ahmed.trackpoop.domain.repository.PostRepository
import com.ahmed.trackpoop.domain.repository.UserRepository

class PostDetailsViewModel(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    savedStateHandle: SavedStateHandle,

) : ViewModel() {
    var state by mutableStateOf(PostDetailsState())
        private set
    private val postId: String = savedStateHandle["postId"] ?: ""


    init {
        if (postId.isNotEmpty()) {
            loadPostDetails()
        } else {
            state = state.copy(errorMessage = "Invalid Post ID")
        }
    }

    private fun loadPostDetails() {
        viewModelScope.launch {
            try {
                val post = postRepository.getPostById(postId).data
                if (post != null) {
                    state = state.copy(
                        post = post,
                        isLiked = post.likes.contains(userRepository.getUserProfile().data?._id) == true
                    )
                } else {
                    state = state.copy(errorMessage = "Post not found")
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.message ?: "Failed to load post details")
            }
        }
    }

    fun addComment(content: String) {
        viewModelScope.launch {
            try {
                if (content.isBlank()) {
                    state = state.copy(errorMessage = "Comment cannot be empty")
                }else {
                    postRepository.addComment(postId, CommentDto(content))
                    loadPostDetails() // Refresh details after adding a comment
                    state = state.copy(comment = "") // Clear comment field
                }
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.message ?: "Failed to add comment")
            }
        }
    }

    fun likePost() {
        viewModelScope.launch {
            try {
                if (state.isLiked) {
                    postRepository.unlikePost(postId)
                } else {
                    postRepository.likePost(postId)
                }
                loadPostDetails() // Refresh details after liking/unliking
            } catch (e: Exception) {
                state = state.copy(errorMessage = e.message ?: "Failed to update like status")
            }
        }
    }

    fun onCommentChange(comment: String) {
        state = state.copy(comment = comment)
    }
}