package com.ahmed.trackpoop.presentation.welcome

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.domain.repository.UserRepository

class WelcomeViewModel(
    private val preferencesManager: PreferencesManager,
) : ViewModel() {
    var state by mutableStateOf(WelcomeState())
        private set

    fun checkUserLoggedIn() {
        viewModelScope.launch {
            val isLoggedIn = preferencesManager.getUserToken() != null
            state = state.copy(isLoggedIn = isLoggedIn)

            if (isLoggedIn) {
                val user = UserRepositoryImpl.getUserProfile(preferencesManager.getUserToken().orEmpty())
                if (user.data != null)
                    state = state.copy(user = user.data)
            }
        }
    }

    init {
        checkUserLoggedIn()
    }
}