package com.ahmed.trackpoop.presentation.auth.sign_in

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.repository.AuthRepositoryImpl
import com.ahmed.trackpoop.domain.repository.AuthRepository
import com.ahmed.trackpoop.navigation.Screen
import com.ahmed.trackpoop.utils.validateEmail
import com.ahmed.trackpoop.utils.validatePassword

class SignInViewModel(

    private val context: Context,
) : ViewModel() {
    var state by mutableStateOf(SignInState())
        private set
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    val preferencesManager = PreferencesManager(sharedPreferences)
    fun onEmailChanged(email: String) {
        state = state.copy(email = email, emailError = validateEmail(email))
    }

    fun onPasswordChanged(password: String) {
        state = state.copy(password = password, passwordError = validatePassword(password))
    }

    fun signIn(navigationController: NavController) {
        state = state.copy(emailError = null, passwordError = null, signInError = null)

        val emailError = validateEmail(state.email)
        val passwordError = validatePassword(state.password)

        if (emailError != null || passwordError != null) {
            state = state.copy(emailError = emailError, passwordError = passwordError)
            return
        }

        state = state.copy(isLoading = true)

        viewModelScope.launch {
            val result = AuthRepositoryImpl.signIn(state.email, state.password)

            state = if (result.isSuccessful()) {
                state.copy(isLoading = false)
            } else {
                state.copy(isLoading = false, signInError = result.message)
            }

            if (!state.isLoading && result.isSuccessful() && result.data != null) {
                preferencesManager.saveUserInfo(
                    result.data.token,
                    result.data.refreshToken,

                )
                navigationController.popBackStack()
                navigationController.navigate(Screen.MainScreen.route) {
                    popUpTo(navigationController.graph.startDestinationId) {
                        inclusive = true
                    }
                }
            } else
                Toast.makeText(
                    navigationController.context,
                    state.signInError,
                    Toast.LENGTH_SHORT
                ).show()
        }


    }

}