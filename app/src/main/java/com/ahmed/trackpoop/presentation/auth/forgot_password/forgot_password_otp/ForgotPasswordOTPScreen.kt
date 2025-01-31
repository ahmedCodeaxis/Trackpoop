package com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password_otp


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.compose.koinViewModel
import com.ahmed.trackpoop.R
val alJazeeraYellow = Color(0xFF582900) // Al Jazeera yellow color in HEX

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgetPasswordOTPScreen(
    navController: NavController,
    paddingValues: PaddingValues = PaddingValues(0.dp),
    viewModel: ForgotPasswordOTPViewModel = koinViewModel()
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
                .padding(vertical = 90.dp, horizontal = 20.dp)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                // Logo
                Image(
                    painter = painterResource(id = R.drawable.ic_email_submit_logo),
                    contentDescription = null,
                    modifier = Modifier.size(170.dp)
                )

                // Title
                Text(
                    text = "OTP Verification",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onBackground
                )

                Text(
                    text = "Enter the verification code we just sent to your email address",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    modifier = Modifier.padding(horizontal = 20.dp),
                    lineHeight = 22.sp
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    OtpFields(viewModel)

                    // Resend Code
                    Text(
                        text = "Resend Code",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFFEDEDED),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable { viewModel.resendCode(navController) },
                    )
                }
            }


            // Verify Button
            Button(
                onClick = { viewModel.verifyOtp(navController) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = alJazeeraYellow
                ),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(width = 1.dp, color = Color(0xFFEDEDED))
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (state.isLoading)
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.size(24.dp)
                        )

                    Text(
                        text = "Verify",
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtpFields(viewModel: ForgotPasswordOTPViewModel) {
    val otpFields = viewModel.state.otpFields
    val focusRequesters =
        List(otpFields.size) { FocusRequester() } // Create a FocusRequester for each field

    Row(
        horizontalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(vertical = 20.dp)
    ) {
        // OTP Fields
        otpFields.forEachIndexed { index, _ ->
            TextField(
                value = otpFields[index],
                onValueChange = { viewModel.onOtpFieldChanged(index, it, focusRequesters) },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .size(50.dp)
                    .background(Color(0xFFD7E3E3), shape = RoundedCornerShape(8.dp))
                    .focusRequester(focusRequesters[index]) // Attach FocusRequester
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
fun PreviewForgetPasswordOTPScreen() {
    val navController = rememberNavController()
    ForgetPasswordOTPScreen(navController)
}