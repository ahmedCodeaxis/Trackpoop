package com.ahmed.trackpoop.presentation.profile.profile_view

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.domain.repository.PostRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.navigation.Screen

class ProfileViewModel(
    private val repository: UserRepository,
    private val postRepository: PostRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {
    var state by mutableStateOf(ProfileState())
        private set

    init {
        viewModelScope.launch {
            val user = repository.getUserProfile().data
            val posts = postRepository.getMyPosts().data

            if (user != null) {
                state = state.copy(user = user)
            }

            if (posts != null) {
                state = state.copy(posts = posts)
            }
            
        }
    }

    fun logout(navController: NavController) {
        viewModelScope.launch {
            preferencesManager.clearUserInfo()
            navController.navigate(Screen.SignInScreen.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
            }
        }
    }
}