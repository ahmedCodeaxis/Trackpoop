package com.ahmed.trackpoop.presentation.main.explore

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.user.AddUserPlantDto
import com.ahmed.trackpoop.domain.repository.PlantRepository
import com.ahmed.trackpoop.domain.repository.UserRepository

class ExploreViewModel(
    private val plantRepository: PlantRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    var state by mutableStateOf(ExploreState())
        private set

    init {
        refreshPlants()
    }

    fun onTabSelected(tab: ExploreTab) {
        state = state.copy(selectedTab = tab)
    }

    fun onSearchQueryChange(query: String) {
        state = state.copy(searchQuery = query)
        // TODO: Implement search logic using plantRepository
    }

    fun refreshPlants() {
        loadPlants()
        loadMyPlants()
    }

    fun loadPlants() {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = "")
            try {
                val plants = plantRepository.getPlants().data ?: emptyList()
                state = state.copy(plants = plants)
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun loadMyPlants () {
        viewModelScope.launch {
            state = state.copy(isLoading = true, error = "")
            try {
                val userPlants = userRepository.getUserProfile().data?.myPlants ?: emptyList()
                state = state.copy(userPlants = userPlants)
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }

    fun addToMyPlants(id: String) {
        viewModelScope.launch {
            state = state.copy(isAddLoading = true, error = "")
            try {
               userRepository.addUserPlant(AddUserPlantDto(id))
            } catch (e: Exception) {
                state = state.copy(error = e.message ?: "An error occurred")
            } finally {
                state = state.copy(isAddLoading = false)
            }

            loadMyPlants()
            loadPlants()
        }
    }
}