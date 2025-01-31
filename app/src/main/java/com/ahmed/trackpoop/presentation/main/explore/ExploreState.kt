package com.ahmed.trackpoop.presentation.main.explore

import com.ahmed.trackpoop.domain.model.Plant
import com.ahmed.trackpoop.domain.model.UserPlant

data class ExploreState(
    val isLoading: Boolean = false,
    val isAddLoading: Boolean = false,
    val error: String = "",
    val selectedTab: ExploreTab = ExploreTab.Plants,
    val searchQuery: String = "",

    val plants: List<Plant> = emptyList(),
    val userPlants: List<UserPlant> = emptyList()
)

enum class ExploreTab {
    Plants, Trees
}