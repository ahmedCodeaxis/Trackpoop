package com.ahmed.trackpoop.presentation.auth.forgot_password.reset_password

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import org.koin.core.KoinApplication
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CPasswordField
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.PresentationModule
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: ResetPasswordViewModel = koinViewModel()
) {

    val state = viewModel.state

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0XFFF7B800))
            .padding(paddingValues)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(vertical = 50.dp, horizontal = 20.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Header with Logo and Title
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_email_submit_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(150.dp)
                )

                Text(
                    text = "Reset Password",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Create your new password",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            // Password Fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // New Password Field
                CPasswordField(
                    label = "New Password",
                    value = state.password,
                    onValueChange = { viewModel.onPasswordChanged(it) },
                    placeholder = "Enter your new password",
                    errorMessage = state.passwordError,
                )

                // Confirm Password Field
                CPasswordField(
                    label = "Confirm Password",
                    value = state.confirmPassword,
                    onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                    placeholder = "Confirm your new password",
                    errorMessage = state.confirmPasswordError,
                )
            }

            // Continue Button
            Button(
                onClick = {
                    viewModel.resetPassword(navController)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = alJazeeraYellow
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onPrimary),
                shape = RoundedCornerShape(28.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (state.isLoading)
                        CircularProgressIndicator(
                            color = Color(0xFFEDEDED),
                            modifier = Modifier.size(24.dp)
                        )


                    Text(
                        text = "Continue",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}


@Composable
@Preview
fun PreviewResetPasswordScreen() {

    TrackPoopTheme {
        val navController = rememberNavController()
        ResetPasswordScreen(navController = navController)
    }
}
