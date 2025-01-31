package com.ahmed.trackpoop.di

import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password.ForgotPasswordViewModel
import com.ahmed.trackpoop.presentation.auth.forgot_password.forgot_password_otp.ForgotPasswordOTPViewModel
import com.ahmed.trackpoop.presentation.auth.forgot_password.reset_password.ResetPasswordViewModel
import com.ahmed.trackpoop.presentation.auth.sign_in.SignInViewModel
import com.ahmed.trackpoop.presentation.auth.sign_up.sign_up.SignUpViewModel
import com.ahmed.trackpoop.presentation.auth.sign_up.sign_up_otp.SignUpOTPViewModel
import com.ahmed.trackpoop.presentation.community.chat.ChatViewModel
import com.ahmed.trackpoop.presentation.community.chats.ChatsViewModel
import com.ahmed.trackpoop.presentation.main.gemini.GeminiChatViewModel
import com.ahmed.trackpoop.presentation.main.explore.ExploreViewModel
import com.ahmed.trackpoop.presentation.main.my_plants.MyPlantsViewModel
import com.ahmed.trackpoop.presentation.main.my_plants.reminders.ReminderViewModel
import com.ahmed.trackpoop.presentation.poop.PoopViewModel
import com.ahmed.trackpoop.presentation.post.post.PostsViewModel
import com.ahmed.trackpoop.presentation.post.post_details.PostDetailsViewModel

import com.ahmed.trackpoop.presentation.profile.change_password.ChangePasswordViewModel
import com.ahmed.trackpoop.presentation.profile.edit_profile.EditProfileViewModel
import com.ahmed.trackpoop.presentation.profile.profile_settings.ProfileSettingsViewModel
import com.ahmed.trackpoop.presentation.profile.profile_view.ProfileViewModel
import com.ahmed.trackpoop.presentation.welcome.WelcomeViewModel

val PresentationModule = module {
    viewModel { WelcomeViewModel(get()) }
    viewModel { SignInViewModel(get()) }

    viewModel { SignUpViewModel(get()) }
    viewModel { SignUpOTPViewModel(get(), get()) }

    viewModel { ForgotPasswordViewModel(get()) }
    viewModel { ForgotPasswordOTPViewModel(get(), get()) }
    viewModel { ResetPasswordViewModel(get(), get()) }

    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { ProfileSettingsViewModel() }
    viewModel { EditProfileViewModel() }
    viewModel { ChangePasswordViewModel() }

    viewModel { ReminderViewModel(get()) }

    viewModel { PostDetailsViewModel(get(), get(), get()) }

    viewModel { ChatsViewModel() }
    viewModel { ChatViewModel() }

    //viewModel { PlantIdentifyViewModel(get(), get()) }

    viewModel { MyPlantsViewModel(get()) }
    viewModel { PostsViewModel(get(), get(), get()) }

    viewModel { ExploreViewModel(get(), get()) }
   // viewModel { PoopViewModel(get()) } // Ensure repositories are passed here

   // viewModel { WeatherViewModel() }
    viewModel { GeminiChatViewModel() }

}