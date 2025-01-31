package com.ahmed.trackpoop.presentation.main.my_plants.reminders

import com.ahmed.trackpoop.domain.model.UserPlant

enum class ReminderFilter {
    ALL, WATER, FERTILIZER
}

data class ReminderState(
    val selectedFilter: ReminderFilter = ReminderFilter.ALL,

    val reminders: List<UserPlant> = emptyList(),
    val todayReminders: List<UserPlant> = emptyList(),
    val upcomingReminders: List<UserPlant> = emptyList(),

    val isLoading: Boolean = false,
    val error: String? = null
)

