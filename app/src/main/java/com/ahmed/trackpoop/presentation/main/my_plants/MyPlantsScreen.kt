package com.ahmed.trackpoop.presentation.main.my_plants

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.presentation.main.components.Header
import com.ahmed.trackpoop.presentation.main.my_plants.plants.PlantsView
import com.ahmed.trackpoop.presentation.main.my_plants.reminders.ReminderView

@Composable
fun MyPlantsScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    onTabNavigation: (Int) -> Unit = {},
    viewModel: MyPlantsViewModel = koinViewModel()
) {
    val state = viewModel.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Header(
            navController, title = "Your Garden", subTitle = state.plants.isEmpty().let {
                if (it) {
                    "No Plants"
                } else {
                    "${state.plants.size} plants"
                }
            },
            userImageURL = SERVER_URL + state.user?.image,
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.9f),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(horizontal = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = { viewModel.onTabSelected(0) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),

                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.tabSelected == 0) Color(0xFFECF6EE) else Color.Transparent,
                            contentColor = Color(0xFF676C6B)
                        )
                    ) {
                        Text("Toutes les plantes")
                    }
                    Button(
                        onClick = { viewModel.onTabSelected(1) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (state.tabSelected == 1) Color(0xFFECF6EE) else Color.Transparent,
                            contentColor = Color(0xFF676C6B)
                        )
                    ) {
                        Text("Rappels")
                    }
                }
            }
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            when {
                state.error != null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = state.error,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                else -> {
                    when (state.tabSelected) {
                        0 -> PlantsView(
                            plants = state.plants,
                            onTabNavigation = onTabNavigation,
                            loadPlants = viewModel::loadProfile,
                            isRefreshing = state.isLoading
                        )
                        1 -> ReminderView(
                            isRefreshing = state.isLoading,
                        )
                    }
                }
            }


        }
    }
}

@Preview
@Composable
fun MyPlantsScreenPreview() {
    MyPlantsScreen(navController = rememberNavController())
}