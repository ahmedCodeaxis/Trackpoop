package com.ahmed.trackpoop.presentation.auth.sign_in

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CPasswordField
import com.ahmed.trackpoop.core.components.CTextField
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.PresentationModule
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Divider
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    signInViewModel: SignInViewModel = koinViewModel()
) {
    val state = signInViewModel.state
    
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
                    painter = painterResource(id = R.drawable.ic_login),
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
                    text = "Welcome Back",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = "Sign in to continue",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }

            // Inputs Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                CTextField(
                    placeholder = "Enter your email",
                    label = "Email",
                    icon = R.drawable.ic_email,
                    value = state.email,
                    onValueChange = { signInViewModel.onEmailChanged(it) },
                    errorMessage = state.emailError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                CPasswordField(
                    placeholder = "Enter your password",
                    label = "Password",
                    value = state.password,
                    onValueChange = { signInViewModel.onPasswordChanged(it) },
                    errorMessage = state.passwordError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                TextButton(
                    onClick = { navController.navigate(Screen.ForgotPasswordScreen.route) },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = "Forgot Password?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Actions Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Divider(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f),
                    thickness = 1.dp
                )
                
                Button(
                    onClick = { signInViewModel.signIn(navController) },
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
                            text = "Sign in",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't have an account?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    TextButton(
                        onClick = { navController.navigate(Screen.SignUpScreen.route) }
                                ) {
                        Text(
                            text = "Sign-up?",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    KoinApplication(
        application = {
            modules(listOf(PresentationModule, DataModule))
        }
    ) {
        TrackPoopTheme {
            SignInScreen(navController = rememberNavController())
        }
    }

}
