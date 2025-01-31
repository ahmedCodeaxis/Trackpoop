package com.ahmed.trackpoop.presentation.main.my_plants.reminders

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.presentation.main.my_plants.reminders.components.EmptyReminder
import com.ahmed.trackpoop.presentation.main.my_plants.reminders.components.RemindersList
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReminderView(
    viewModel: ReminderViewModel = koinViewModel(),
    isRefreshing: Boolean = false,
) {
    val state by viewModel.state.collectAsState()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = viewModel::loadPlants
    )
    val isEmpty = state.reminders.filter {
        it.hasReminderInWater || it.hasReminderInFertilize
    }.isEmpty()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = if (isEmpty) Arrangement.Center else Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (isEmpty) {
                item {
                    EmptyReminder()
                }

            } else {
                if (state.reminders.filter {
                        it.isWateredTodayOrOverdue || it.isFertilizedTodayOrOverdue
                    }.isNotEmpty())
                    item {
                        RemindersList(
                            title = "Today's Reminders",
                            reminders = state.todayReminders,
                            selectedFilter = state.selectedFilter,
                            status = true,
                            onUpdateReminder = viewModel::updateReminder
                        )
                    }
                if (state.reminders.filter {
                        it.isWateredInFuture || it.isFertilizedInFuture
                    }.isNotEmpty())
                    item {
                        RemindersList(
                            title = "Upcoming Reminders",
                            reminders = state.upcomingReminders,
                            selectedFilter = state.selectedFilter,
                            status = false,
                        )
                    }
            }
        }
        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Preview
@Composable
fun ReminderScreenPreview() {
    TrackPoopTheme {
        ReminderView()
    }
}