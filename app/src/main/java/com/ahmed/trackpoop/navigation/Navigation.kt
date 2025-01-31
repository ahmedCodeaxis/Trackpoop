package com.ahmed.trackpoop.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.domain.repository.UserRepository

import com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password.ForgetPasswordScreen
import com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password_otp.ForgetPasswordOTPScreen
import com.ahmed.trackpoop.presentation.auth.forgot_password.reset_password.ResetPasswordScreen
import com.ahmed.trackpoop.presentation.auth.sign_in.SignInScreen
import com.ahmed.trackpoop.presentation.auth.sign_up.sign_up.SignUpScreen
import com.ahmed.trackpoop.presentation.auth.sign_up.sign_up_otp.SignUpOTPScreen
import com.ahmed.trackpoop.presentation.community.chat.ChatScreen
import com.ahmed.trackpoop.presentation.community.chats.ChatsScreen
import com.ahmed.trackpoop.presentation.main.gemini.GeminiChatScreen
import com.ahmed.trackpoop.presentation.main.MainScreen
import com.ahmed.trackpoop.presentation.on_boarding.OnBoardingScreen
import com.ahmed.trackpoop.presentation.poop.PoopHistoryScreen
import com.ahmed.trackpoop.presentation.post.post_details.PostDetailsScreen
import com.ahmed.trackpoop.presentation.profile.change_password.ChangePasswordScreen
import com.ahmed.trackpoop.presentation.profile.edit_profile.EditProfileScreen
import com.ahmed.trackpoop.presentation.profile.profile_settings.ProfileSettingsScreen
import com.ahmed.trackpoop.presentation.profile.profile_view.ProfileScreen
import com.ahmed.trackpoop.presentation.welcome.WelcomeScreen

@Composable
fun Navigation(
    paddingValues: PaddingValues,
    preferencesManager: PreferencesManager,


) {
    val navController = rememberNavController()
    val startDestination = if (preferencesManager.isLoggedIn()) {
        Screen.MainScreen.route
    } else {
        Screen.OnBoardingScreen.route
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable(route = Screen.OnBoardingScreen.route) {
            OnBoardingScreen(navController, paddingValues)
        }

        composable(route = Screen.WelcomeScreen.route) {
            WelcomeScreen(navController, paddingValues)
        }

        composable(route = Screen.SignInScreen.route) {
            SignInScreen(navController, paddingValues)
        }

        composable(route = Screen.ForgotPasswordScreen.route) {
            ForgetPasswordScreen(navController, paddingValues)
        }
        composable(
            route = Screen.ForgotPasswordOTPScreen.route + "/{email}"
        ) {
            ForgetPasswordOTPScreen(navController, paddingValues)
        }
        composable(route = Screen.ResetPasswordScreen.route + "/{email}") {
            ResetPasswordScreen(navController, paddingValues)
        }

        composable(route = Screen.SignUpScreen.route) {
            SignUpScreen(navController, paddingValues)
        }
        composable(route = Screen.SignUpOTPScreen.route + "/{email}") {
            SignUpOTPScreen(navController, paddingValues)
        }
        composable(route = Screen.ChatsScreen.route) {
            ChatsScreen(navController, paddingValues)
        }

        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen(navController, paddingValues)
        }

        composable(route = Screen.ProfileSettingScreen.route) {
            ProfileSettingsScreen(navController, paddingValues)
        }

        composable(route = Screen.EditProfileScreen.route) {
            EditProfileScreen(navController, paddingValues)
        }
        composable(route = Screen.ChangePasswordScreen.route) {
            ChangePasswordScreen(navController, paddingValues)
        }

        composable(route = Screen.MainScreen.route) {
            MainScreen(navController, paddingValues)
        }

        composable(route = Screen.ChatsScreen.route) {
            ChatsScreen(navController, paddingValues)
        }

        composable(route = Screen.ChatScreen.route) {
            ChatScreen(navController, paddingValues)
        }
        composable(route = Screen.PoopHistoryScreen.route) {
            /*PoopHistoryScreen(
                navController = navController,
                poopRepository = poopRepository, // Pass the repository here
                userRepository = userRepository // Pass the repository here
            )*/

            /* composable(Screen.ArScreen.route) {
            ArScreen()
        }   
        composable(Screen.ArAlbumScreen.route) {
            ArAlbumScreen(navController = navController, paddingValues)
        }

        composable(Screen.PlantIdentifyScreen.route) {
            PlantIdentifyScreen(navController, paddingValues)
        }
*/
            composable(Screen.PostDetailsScreen.route + "/{postId}") { backStackEntry ->
                PostDetailsScreen(navController)
            }
            /*
        composable(Screen.WeatherScreen.route){
            WeatherScreen(paddingValues)
        }*/
            composable(route = Screen.GeminiChatScreen.route) {
                GeminiChatScreen(navController, paddingValues)
            }
        }
    }}
