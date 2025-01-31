package com.ahmed.trackpoop.presentation.profile.profile_settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.RestoreFromTrash
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.presentation.profile.profile_settings.components.ProfileActionButton
import com.ahmed.trackpoop.presentation.profile.profile_settings.components.ProfileHeader
import com.ahmed.trackpoop.presentation.profile.profile_settings.components.ProfileOptions
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileSettingsScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    profileSettingsViewModel: ProfileSettingsViewModel = koinViewModel()
) {
    val state = profileSettingsViewModel.state
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top curved background
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .background(
                        MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(bottomStart = 170.dp, bottomEnd = 170.dp)
                    )
                    .padding(top = paddingValues.calculateTopPadding())
            ) {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-100).dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                item {
                    ProfileHeader(state.name, state.email, state.badge, SERVER_URL + state.image)
                }

                item {
                    ProfileOptions(navController)
                }

                item {
                    ProfileActionButton(
                        icon = Icons.Default.RestoreFromTrash,
                        text = "Delete Account",
                        textColor = MaterialTheme.colorScheme.error,
                        iconTint = MaterialTheme.colorScheme.error,
                        onClick = { profileSettingsViewModel.deleteAccount(navController, context) }
                    )
                }

                item {
                    ProfileActionButton(
                        icon = Icons.AutoMirrored.Filled.Logout,
                        text = "Logout",
                        textColor = MaterialTheme.colorScheme.primary,
                        iconTint = MaterialTheme.colorScheme.primary,
                        onClick = { profileSettingsViewModel.logout(navController, context) }
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    TrackPoopTheme {
        ProfileSettingsScreen(navController = rememberNavController())
    }
}
