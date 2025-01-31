package com.ahmed.trackpoop.presentation.profile.edit_profile

import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CTextField
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.PresentationModule
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext
val darkBrown = Color(0xFF3E2723)
val lightBrown = Color(0xFF795548)
val poopBrown = Color(0xFF5D4037)

// Poop Theme Definition

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(
    navigationController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    editProfileViewModel: EditProfileViewModel = koinViewModel()
) {
    var state = editProfileViewModel.state
    val context = LocalContext.current

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        editProfileViewModel.onImagePicked(uri)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(darkBrown)
            .padding(paddingValues)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .animateContentSize(animationSpec = tween(300)),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Profile Picture Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .shadow(12.dp, CircleShape)
                        .clip(CircleShape)
                        .clickable { imagePickerLauncher.launch("image/*") }
                ) {
                    AsyncImage(
                        model = state.imageUri ?: state.image,
                        contentDescription = "Profile Image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        fallback = painterResource(id = R.drawable.user_profile)
                    )
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = "Change Image",
                            tint = Color.White
                        )
                    }
                }

                if (state.imageError != null) {
                    Text(
                        text = state.imageError!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Input Fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CTextField(
                    label = "Full Name",
                    labelColor = Color.White.copy(alpha = 0.8f),
                    placeholder = "Full Name",
                    value = state.name,
                    onValueChange = { editProfileViewModel.onNameChanged(it) },
                    errorMessage = state.nameError,
                    icon = R.drawable.ic_user
                )

                CTextField(
                    label = "Email",
                    labelColor = Color.White.copy(alpha = 0.8f),
                    placeholder = "Email",
                    value = state.email,
                    onValueChange = { editProfileViewModel.onEmailChanged(it) },
                    errorMessage = state.emailError,
                    icon = R.drawable.ic_email
                )

                CTextField(
                    label = "Phone Number",
                    labelColor = Color.White.copy(alpha = 0.8f),
                    placeholder = "Phone Number",
                    value = state.phone,
                    onValueChange = { editProfileViewModel.onPhoneChanged(it) },
                    errorMessage = state.phoneError,
                    icon = R.drawable.phone_icon
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Save Button
            Button(
                onClick = { editProfileViewModel.editProfile(navigationController, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(
                    containerColor = poopBrown
// Dark Brown Button
                ),
                enabled = !state.isLoading
            ) {
                AnimatedVisibility(visible = state.isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
                AnimatedVisibility(visible = !state.isLoading) {
                    Text(
                        text = "Save Changes",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}


