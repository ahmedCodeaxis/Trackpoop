package com.ahmed.trackpoop.presentation.poop

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.repository.PoopRepositoryImpl
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import kotlinx.coroutines.launch

class PoopViewModel(
  //  private val context: Context
): ViewModel() {

    var state by mutableStateOf(PoopState())
        private set



     fun getPoop(context: Context) {
        state = state.copy(isLoading = true) // ✅ Start loading
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val preferencesManager = PreferencesManager(sharedPreferences)
        viewModelScope.launch {
            try {
                val response = PoopRepositoryImpl.getPoop(preferencesManager.getUserToken().orEmpty())
                state = state.copy(
                    poop = response.data ?: emptyList(),
                    error = null
                )
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            } finally {
                state = state.copy(isLoading = false) // ✅ Stop loading
            }
        }
    }

     fun getUser(context: Context) {
        viewModelScope.launch {
            try {
                val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val preferencesManager = PreferencesManager(sharedPreferences)
                val token = preferencesManager.getUserToken()
                val user = UserRepositoryImpl.getUserProfile(token.orEmpty()).data
                if (user != null) {
                    state = state.copy(user = user, userId = user._id)
                }
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "Failed to load user")
            }
        }
    }

}
