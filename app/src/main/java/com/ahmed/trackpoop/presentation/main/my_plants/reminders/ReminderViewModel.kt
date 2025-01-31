package com.ahmed.trackpoop.presentation.main.my_plants.reminders

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.remote.dto.user.FertilizeReminderDto
import com.ahmed.trackpoop.data.remote.dto.user.WaterReminderDto
import com.ahmed.trackpoop.domain.model.ReminderType
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.domain.repository.UserRepository

class ReminderViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ReminderState())
    val state = _state.asStateFlow()

    init {
        loadPlants()
    }

    fun loadPlants() {
        viewModelScope.launch {
            val reminders = userRepository.getUserProfile().data?.myPlants ?: emptyList()
            updatePlantsAndFilter(reminders)
        }
    }

    fun onFilterSelected(filter: ReminderFilter) {
        _state.update { it.copy(selectedFilter = filter) }
    }

    private fun updatePlantsAndFilter(reminders: List<UserPlant>) {
        val todayReminders = reminders.filter { reminder ->
            reminder.isWateredTodayOrOverdue || reminder.isFertilizedTodayOrOverdue
        }
        val upcomingReminders = reminders.filter { plant ->
            plant.isWateredInFuture || plant.isFertilizedInFuture
        }

        _state.update {
            it.copy(
                reminders = reminders,
                todayReminders = todayReminders,
                upcomingReminders = upcomingReminders
            )
        }
    }

    fun updateReminder(plant: UserPlant, type: ReminderType) {
        viewModelScope.launch {
            try {
                when (type) {
                    ReminderType.WATER -> {
                        userRepository.updateWaterReminder(
                            plant.plant._id,
                            WaterReminderDto(
                                lastTimeWatered = null,
                                waterAmount = plant.waterReminder.waterAmount?.toDouble() ?: 0.0,
                                waterFrequency = plant.waterReminder.waterFrequency ?: 0
                            )
                        )
                    }

                    ReminderType.FERTILIZER -> {
                        userRepository.updateFertilizeReminder(
                            plant.plant._id,
                            FertilizeReminderDto(
                                lastTimeFertilized = null,
                                fertilizeAmount = plant.fertilizeReminder.fertilizeAmount?.toDouble()
                                    ?: 0.0,
                                fertilizeFrequency = plant.fertilizeReminder.fertilizeFrequency ?: 0
                            )
                        )
                    }
                }
                loadPlants()
            } catch (e: Exception) {
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}