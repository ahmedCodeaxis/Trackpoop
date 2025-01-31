package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CTextField
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.PresentationModule
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordScreen(
    navigationController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: ForgotPasswordViewModel = koinViewModel()
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
                .fillMaxSize()
                .padding(24.dp)
                .animateContentSize(animationSpec = tween(300)),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Logo Section
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(vertical = 32.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_email_submit_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .shadow(8.dp, CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .padding(16.dp),
                    contentScale = ContentScale.Fit
                )
                
                Spacer(modifier = Modifier.height(24.dp))
                
                Text(
                    text = "Forgot Password?",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = "Enter your email to receive a verification code",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            // Email Field
            CTextField(
                label = "Email",
                placeholder = "Enter your email",
                value = state.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                icon = R.drawable.ic_email,
                errorMessage = state.emailError,
                textColor = MaterialTheme.colorScheme.onBackground,
                labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
            )

            Spacer(modifier = Modifier.weight(1f))

            // Actions
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { viewModel.forgotPassword(navigationController) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(28.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = alJazeeraYellow
                    ),
                    enabled = !state.isLoading
                ) {
                    AnimatedVisibility(visible = state.isLoading) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    AnimatedVisibility(visible = !state.isLoading) {
                        Text(
                            text = "Continue",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                TextButton(
                    onClick = { navigationController.navigate(Screen.SignInScreen.route) }
                ) {
                    Text(
                        text = "Back to Sign in",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ForgetPasswordScreenPreview() {
    KoinApplication(
        application = {
            modules(listOf(PresentationModule, DataModule))
        }
    ) {
        TrackPoopTheme {
            val navController = rememberNavController()
            ForgetPasswordScreen(navController)
        }
    }
}