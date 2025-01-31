package com.ahmed.trackpoop.navigation

sealed class Screen(val route: String) {
    object OnBoardingScreen: Screen("on_boarding_screen")
    object WelcomeScreen: Screen("welcome_screen")

    object SignInScreen: Screen("sign_in_screen")

    object ForgotPasswordScreen: Screen("forgot_password_screen")
    object ForgotPasswordOTPScreen: Screen("forgot_password_otp_screen")
    object ResetPasswordScreen: Screen("reset_password_screen")

    object SignUpScreen: Screen("sign_up_screen")
    object SignUpOTPScreen: Screen("sign_up_otp_screen")
    
    object ChatsScreen : Screen("chats_screen")
    object ChatScreen : Screen("chat_screen")

    object ProfileSettingScreen: Screen("profile_settings_screen")
    object ProfileScreen: Screen("profile_screen")
    object EditProfileScreen : Screen("Edit_ProfileScreen")
    object ChangePasswordScreen : Screen("Change_PasswordScreen")

    object PostDetailsScreen : Screen("post_details_screen")

    object MainScreen: Screen("main_screen")
    object HomeScreen: Screen("home_screen")

    object ArScreen : Screen("ar_screen")
    object ArAlbumScreen : Screen("ar_album_screen")

    object PlantIdentifyScreen : Screen("plant_identify_screen")

    object WeatherScreen : Screen("weatherscreen")
    object GeminiChatScreen : Screen("chatPage")

    object  PoopHistoryScreen : Screen("poop_history_screen")


}