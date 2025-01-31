package com.ahmed.trackpoop.presentation.profile.profile_view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.domain.model.Post
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import com.ahmed.trackpoop.di.SERVER_URL
import com.ahmed.trackpoop.navigation.Screen
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProfileScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    profileViewModel: ProfileViewModel = koinViewModel()
) {
    val state = profileViewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Profile Header with curved bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
            ) {
                // Background gradient
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary,
                                    MaterialTheme.colorScheme.primaryContainer
                                )
                            )
                        )
                        .padding(top = 16.dp)

                )

                // Add back button
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White.copy(alpha = 0.2f), shape = CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(40.dp))

                    AsyncImage(
                        model = SERVER_URL + state.user.image,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .border(3.dp, MaterialTheme.colorScheme.surface, CircleShape),
                        contentScale = ContentScale.Crop,
                        fallback = painterResource(id = R.drawable.user_profile)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // User info
                    Text(
                        text = state.user.name,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF6A4E23) // Dark Brown for poop theme
                        )
                    )

                    // Stats row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatItem(
                            value = state.posts.size.toString(),
                            label = "Posts",
                            color = Color(0xFF6A4E23) // Dark Brown for poop theme
                        )
                        StatItem(
                            value = state.user.score.toString(),
                            label = "Score",
                            color = Color(0xFF6A4E23) // Dark Brown for poop theme
                        )
                        StatItem(
                            value = state.user.badge.toString(),
                            label = "Badge",
                            color = Color(0xFF6A4E23) // Dark Brown for poop theme
                        )
                    }
                }
            }

            // Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                OutlinedButton(
                    onClick = { navController.navigate(Screen.ProfileSettingScreen.route) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFD5B58A),  // Beige background for poop theme
                        contentColor = Color(0xFF6A4E23) // Dark Brown for text
                    ),
                    border = BorderStroke(1.dp, Color(0xFF6A4E23)) // Dark Brown border
                ) {
                    Text("Edit Profile", color = Color(0xFF6A4E23)) // Dark Brown text
                }

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedButton(
                    onClick = { profileViewModel.logout(navController) },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color(0xFFD5B58A), // Beige background for logout button
                        contentColor = Color(0xFF6A4E23) // Dark Brown for logout text
                    ),
                    border = BorderStroke(1.dp, Color(0xFF6A4E23)) // Dark Brown border
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            tint = Color(0xFF6A4E23) // Dark Brown for logout icon
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Logout", color = Color(0xFF6A4E23)) // Dark Brown text
                    }
                }
            }

            // Posts grid with modern look
            PostsGrid(
                posts = state.posts,
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp)
            )
        }
    }
}

@Composable
private fun StatItem(value: String, label: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                color = color
            )
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = color.copy(alpha = 0.8f)
        )
    }
}

@Composable
private fun PostsGrid(
    posts: List<Post>,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp),
    ) {
        items(posts.size) { index ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(Screen.PostDetailsScreen.route + "/${posts[index]._id}")
                    }
            ) {
                AsyncImage(
                    model = SERVER_URL + posts[index].image,
                    contentDescription = "Post Image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    fallback = painterResource(id = R.drawable.blue)
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    TrackPoopTheme {
        ProfileScreen(
            navController = rememberNavController()
        )
    }
}
