package com.ahmed.trackpoop.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.data.remote.PoopApiService
import com.ahmed.trackpoop.data.remote.UserApiService
import com.ahmed.trackpoop.data.repository.PoopRepositoryImpl
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.presentation.main.my_plants.MyPlantsScreen
import com.ahmed.trackpoop.presentation.poop.PoopHistoryScreen
import com.ahmed.trackpoop.presentation.post.post.PostsScreen

import com.ahmed.trackpoop.presentation.profile.profile_settings.ProfileSettingsScreen

@Composable
fun MainScreen(navController: NavController, paddingValues: PaddingValues = PaddingValues(0.dp)) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val context = navController.context // Get the context from navController

    Box(
        modifier = Modifier
            .fillMaxSize()
            //.background(MaterialTheme.colorScheme.background)
            .background(Color(0xFFF7B200))

    ) {
        Box(
            modifier = Modifier.padding(bottom = 72.dp)
                .background(Color(0xFFF7B200))

        ) {
            when (selectedTab) {
                0 -> {
                   PoopHistoryScreen(navController)
                }

                1 -> {
                    ProfileSettingsScreen(navController, paddingValues)
                }


                3 -> {
                    PostsScreen(navController, paddingValues)
                }

                4 -> {
                    MyPlantsScreen(navController, paddingValues, onTabNavigation = { selectedTab = it })
                }
            }
        }


        if (selectedTab != 2) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                BottomNavigationBar(

                    navController = navController,
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it },
                    paddingValues
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    navController: NavController,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit,
    paddingValues: PaddingValues
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.BottomCenter
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 8.dp,
                    shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
                )
                //.background(Color.White)
                .background(Color(0xFFF7B200))

                .padding(bottom = paddingValues.calculateBottomPadding())
                .height(72.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                NavBarItem(
                    icon = R.drawable.home,
                    label = "Home",
                    selected = selectedTab == 0,
                    onClick = { onTabSelected(0) }
                )

                NavBarItem(
                    icon = R.drawable.explore_icon,
                    label = "Explorer",
                    selected = selectedTab == 1,
                    onClick = { onTabSelected(1) }
                )

                Spacer(modifier = Modifier.width(72.dp))

                NavBarItem(
                    icon = R.drawable.diagnostics,
                    label = "Plant AI",
                    selected = selectedTab == 3,
                    onClick = { onTabSelected(3) }
                )

                NavBarItem(
                    icon = R.drawable.plant_icon,
                    label = "My Plants",
                    selected = selectedTab == 4,
                    onClick = { onTabSelected(4) }
                )
            }
        }

        // Floating Action Button
        FloatingActionButton(
            onClick = { navController.navigate(Screen.ArScreen.route) },
            modifier = Modifier
                .width(70.dp)
                .height(70.dp)
                .offset(y = (-80).dp, x = 0.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
        ) {
            Icon(
                painter = painterResource(id = R.drawable.scan_icon),
                contentDescription = "Scan",
                modifier = Modifier.size(35.dp)
            )
        }
    }
}

@Composable
private fun NavBarItem(
    icon: Int,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(8.dp)
            .width(50.dp)
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = label,
            tint = if (selected) MaterialTheme.colorScheme.primary else Color(0xFF525252),
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = label,
            color = if (selected) MaterialTheme.colorScheme.primary else Color(0xFF525252),
            style = MaterialTheme.typography.labelSmall,
            textAlign = TextAlign.Center,
            fontSize = 12.sp
        )
    }
}

