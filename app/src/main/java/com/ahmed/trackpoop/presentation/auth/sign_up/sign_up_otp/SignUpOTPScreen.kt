package com.ahmed.trackpoop.presentation.auth.sign_up.sign_up_otp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.KoinApplication
import com.ahmed.trackpoop.R
import com.ahmed.trackpoop.di.DataModule
import com.ahmed.trackpoop.di.PresentationModule
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import kotlin.text.isEmpty
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpOTPScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    signUpOTPViewModel: SignUpOTPViewModel = koinViewModel()
) {
    val state = signUpOTPViewModel.state

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
                    text = "Account Verification",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )
                
                Text(
                    text = "Enter the verification code we sent to your email",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Center
                )
            }

            // OTP Fields
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OtpFields(viewModel = signUpOTPViewModel)

                TextButton(
                    onClick = { signUpOTPViewModel.resendCode(navController) }
                ) {
                    Text(
                        text = "Resend Code",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // Action Button
            Button(
                onClick = { signUpOTPViewModel.verifyOtp(navController) },
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
                        text = "Verify",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpFields(viewModel: SignUpOTPViewModel) {
    val otpFields = viewModel.state.otpFields
    val focusRequesters = List(otpFields.size) { FocusRequester() }

    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.padding(vertical = 16.dp)
    ) {
        otpFields.forEachIndexed { index, _ ->
            TextField(
                value = otpFields[index],
                onValueChange = { viewModel.onOtpFieldChanged(index, it, focusRequesters) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .size(56.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp))
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant,
                        RoundedCornerShape(12.dp)
                    )
                    .focusRequester(focusRequesters[index])
                    .onKeyEvent { keyEvent ->
                        // Check if the native key code is for backspace
                        if (keyEvent.key == Key.Backspace && otpFields[index].isEmpty() && index > 0) {
                            focusRequesters[index - 1].requestFocus()
                        }
                        false
                    },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignUpOTPScreen() {
    KoinApplication(
        application = {
            modules(listOf(PresentationModule, DataModule))
        }
    ) {
        TrackPoopTheme {
            val navController = rememberNavController()
            SignUpOTPScreen(navController)
        }
    }
}
