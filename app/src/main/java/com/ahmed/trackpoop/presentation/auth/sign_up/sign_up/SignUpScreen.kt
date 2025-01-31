package com.ahmed.trackpoop.presentation.auth.sign_up.sign_up

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.core.components.CPasswordField
import com.ahmed.trackpoop.core.components.CTextField
import com.ahmed.trackpoop.navigation.Screen
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    signUpViewModel: SignUpViewModel = koinViewModel()
) {
    val state = signUpViewModel.state

    var imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        signUpViewModel.onImageSelected(uri)
    }

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
//            // Logo Section
//            Column(
//                horizontalAlignment = Alignment.CenterHorizontally,
//                modifier = Modifier.padding(vertical = 32.dp)
//            ) {
//                Image(
//                    painter = painterResource(id = R.drawable.ic_login),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(100.dp)
//                        .shadow(8.dp, CircleShape)
//                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
//                        .padding(16.dp),
//                    contentScale = ContentScale.Fit
//                )
//
//                Spacer(modifier = Modifier.height(24.dp))
//
//                Text(
//                    text = "Create Account",
//                    style = MaterialTheme.typography.headlineMedium.copy(
//                        fontWeight = FontWeight.Bold
//                    ),
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//            }

            // Input Fields
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Profile Picture Selection
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .shadow(4.dp, CircleShape)
                            .background(MaterialTheme.colorScheme.surface, CircleShape)
                            .clickable { imagePickerLauncher.launch("image/*") }
                    ) {
                        if (state.image != null) {
                            AsyncImage(
                                model = state.image,
                                contentDescription = "Profile Picture",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = "Add Profile Picture",
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.Center),
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                    if (state.imageError != null) {
                        Text(
                            text = state.imageError,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Text(
                        text = "Create Account",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colorScheme.onBackground
                    )

                }

                CTextField(
                    label = "Name",
                    placeholder = "Enter your name",
                    value = state.name,
                    onValueChange = { signUpViewModel.onNameChanged(it) },
                    icon = R.drawable.ic_user,
                    errorMessage = state.nameError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                CTextField(
                    label = "Email",
                    placeholder = "Enter your email",
                    value = state.email,
                    onValueChange = { signUpViewModel.onEmailChanged(it) },
                    icon = R.drawable.ic_email,
                    errorMessage = state.emailError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                CPasswordField(
                    label = "Password",
                    placeholder = "Enter your password",
                    value = state.password,
                    onValueChange = { signUpViewModel.onPasswordChanged(it) },
                    errorMessage = state.passwordError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                )

                CTextField(
                    label = "Phone Number",
                    placeholder = "Enter your phone number",
                    value = state.phone,
                    onValueChange = { signUpViewModel.onphoneChanged(it) },
                    icon = R.drawable.phone_icon,
                    errorMessage = state.phoneError,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    labelColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Actions Section
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = { signUpViewModel.signUp(navController) },
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
                            text = "Create Account",
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
                        text = "Already have an account? ",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                    TextButton(onClick = { navController.navigate(Screen.SignInScreen.route) }) {
                        Text(
                            text = "Sign in",
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
}

@Preview
@Composable
fun SignUpScreenPreview() {
    val navController = rememberNavController()
    SignUpScreen(navController)
}
