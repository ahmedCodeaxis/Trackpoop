package com.ahmed.trackpoop.presentation.main.my_plants

import com.ahmed.trackpoop.domain.model.User
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.domain.model.emptyUser

data class MyPlantsState(
    val user: User? = emptyUser,
    val plants: List<UserPlant> = emptyList(),
    val tabSelected: Int = 0,
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPlant: UserPlant? = null,
    val showBottomSheet: Boolean = false
)
