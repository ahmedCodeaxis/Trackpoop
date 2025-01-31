    package com.ahmed.trackpoop.presentation.profile.profile_settings
    
    import android.content.Context
    import android.content.SharedPreferences
    import android.util.Log
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.setValue
    import androidx.lifecycle.ViewModel
    import androidx.lifecycle.viewModelScope
    import androidx.navigation.NavController
    import kotlinx.coroutines.launch
    import com.ahmed.trackpoop.data.preferences.PreferencesManager
    import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
    import com.ahmed.trackpoop.domain.repository.UserRepository
    import com.ahmed.trackpoop.navigation.Screen
    
    class ProfileSettingsViewModel
       
     : ViewModel() {
        var state by mutableStateOf(ProfileSettingsState())
            private set
    
        /*init {
            viewModelScope.launch {
                val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val preferencesManager = PreferencesManager(sharedPreferences)
                val token = preferencesManager.getUserToken()
                val user = UserRepositoryImpl.getUserProfile(preferencesManager.getUserToken().orEmpty()).data
                if (user != null) {
                    state = state.copy(name = user.name, email = user.email, badge = user.badge, image = user.image)
                }
            }
        }
    */
        fun deleteAccount(navController: NavController,context: Context) {
            viewModelScope.launch {
                val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
                val preferencesManager = PreferencesManager(sharedPreferences)
                val token = preferencesManager.getUserToken()
                val response = UserRepositoryImpl.deleteAccount()
                if (response.success) {
                    preferencesManager.clearUserInfo()
                    navController.navigate(Screen.SignInScreen.route)
                } else {
                    Log.e("ProfileViewModel", "deleteAccount: ${response.message}")
                }
            }
        }
    
        fun logout(navigationController: NavController,context: Context) {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val preferencesManager = PreferencesManager(sharedPreferences)
            val token = preferencesManager.getUserToken()
            preferencesManager.clearUserInfo()
            navigationController.navigate(Screen.SignInScreen.route)
        }
    
    }