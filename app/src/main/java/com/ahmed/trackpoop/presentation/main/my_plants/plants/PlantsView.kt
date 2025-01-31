package com.ahmed.trackpoop.presentation.main.my_plants.plants

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ahmed.trackpoop.core.data.sampleUserPlant
import com.ahmed.trackpoop.domain.model.UserPlant
import com.ahmed.trackpoop.presentation.main.my_plants.plants.components.EmptyList
import com.ahmed.trackpoop.presentation.main.my_plants.plants.components.PlantCard
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PlantsView(
    plants: List<UserPlant> = emptyList(),
    onTabNavigation: (Int) -> Unit = {},
    loadPlants: () -> Unit = {},
    isRefreshing: Boolean = false,
    onPlantClick: (UserPlant) -> Unit = {}  // Add this parameter
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { loadPlants() }
    )

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
            verticalArrangement = if (plants.isEmpty()) Arrangement.Center else Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (plants.isEmpty()) {
                item {
                    EmptyList(
                        onTabNavigation = onTabNavigation
                    )
                }
            } else {
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Plants List", style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        )
                        TextButton(
                            onClick = { onTabNavigation(1) },
                            colors = ButtonDefaults.textButtonColors(
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text(
                                "Add Plant", style = TextStyle(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }

                items(plants) { plant ->
                    PlantCard(
                        plant = plant,
                        onClick = { onPlantClick(plant) }  // Add onClick handler
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
fun PlantsListScreenPreview() {
    TrackPoopTheme {
        PlantsView(plants = listOf(sampleUserPlant))
    }
}