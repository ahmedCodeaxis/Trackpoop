package com.ahmed.trackpoop.presentation.main.my_plants

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.user.FertilizeReminderDto
import com.ahmed.trackpoop.data.remote.dto.user.WaterReminderDto
import com.ahmed.trackpoop.domain.model.ReminderType
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.domain.repository.UserRepository

class MyPlantsViewModel(
    private val userRepository: UserRepository) : ViewModel() {

    var state by mutableStateOf(MyPlantsState())
        private set

    init {
        loadProfile()
    }

    fun onTabSelected (tabSelected : Int){
        state = state.copy(tabSelected = tabSelected)
    }

    fun loadProfile() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            val user = userRepository.getUserProfile().data
            state = state.copy(user = user, plants = user?.myPlants ?: emptyList(), isLoading = false)
        }
    }

    fun showPlantDetails(plant: UserPlant) {
        state = state.copy(
            selectedPlant = plant,
            showBottomSheet = true
        )
    }

    fun hideBottomSheet() {
        state = state.copy(
            showBottomSheet = false,
            selectedPlant = null
        )
    }

    fun setReminder(plantId: String, type: ReminderType, frequency: Int, amount: Double) {
        viewModelScope.launch {
            try {
                state = state.copy(isLoading = true)
                
                val result = when (type) {
                    ReminderType.WATER -> {
                        userRepository.updateWaterReminder(
                            plantId,
                            WaterReminderDto(null, amount, frequency)  // Pass null for lastTimeWatered
                        )
                    }
                    ReminderType.FERTILIZER -> {
                        userRepository.updateFertilizeReminder(
                            plantId,
                            FertilizeReminderDto(null, amount, frequency)  // Pass null for lastTimeFertilized
                        )
                    }
                }
                
                if (result.success) {
                    loadProfile()
                    hideBottomSheet()
                } else {
                    state = state.copy(error = result.message)
                }
            } catch (e: Exception) {
                Log.e("MyPlantsViewModel", "Error setting reminder", e)
                state = state.copy(error = e.message ?: "Unknown error occurred")
            } finally {
                state = state.copy(isLoading = false)
            }
        }
    }
}