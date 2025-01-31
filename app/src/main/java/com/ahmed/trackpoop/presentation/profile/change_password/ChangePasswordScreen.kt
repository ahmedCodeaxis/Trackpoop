package com.ahmed.trackpoop.presentation.profile.change_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CPasswordField
import com.ahmed.trackpoop.ui.theme.*
val darkBrown = Color(0xFF3E2723)
val lightBrown = Color(0xFF795548)
val poopBrown = Color(0xFF5D4037)
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ChangePasswordScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
) {
    val changePasswordViewModel = viewModel<ChangePasswordViewModel>()
    val context = LocalContext.current
    val state = changePasswordViewModel.state

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
            // Header Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_password),
                    contentDescription = null,
                    modifier = Modifier
                        .size(90.dp)
                        .shadow(8.dp, CircleShape)
                        .background(lightBrown, CircleShape)
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Change Password",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
                Text(
                    text = "Create a strong new password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }

            // Password Fields
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                CPasswordField(
                    placeholder = "Current Password",
                    label = "Current Password",
                    value = state.currentPassword,
                    onValueChange = { changePasswordViewModel.onCurrentPasswordChanged(it) },
                    errorMessage = state.currentPasswordError,
                    textColor = Color.White,
                    labelColor = Color.White.copy(alpha = 0.8f)
                )
                CPasswordField(
                    placeholder = "New Password",
                    label = "New Password",
                    value = state.newPassword,
                    onValueChange = { changePasswordViewModel.onNewPasswordChanged(it) },
                    errorMessage = state.newPasswordError,
                    textColor = Color.White,
                    labelColor = Color.White.copy(alpha = 0.8f)
                )
                CPasswordField(
                    placeholder = "Confirm Password",
                    label = "Confirm Password",
                    value = state.confirmPassword,
                    onValueChange = { changePasswordViewModel.onConfirmPasswordChanged(it) },
                    errorMessage = state.confirmPasswordError,
                    textColor = Color.White,
                    labelColor = Color.White.copy(alpha = 0.8f)
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            // Update Button
            Button(
                onClick = { changePasswordViewModel.changePassword(navController, context) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = poopBrown
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
                        text = "Update Password",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewChangePasswordScreen() {
    TrackPoopTheme {
        ChangePasswordScreen(navController = rememberNavController())
    }
}
