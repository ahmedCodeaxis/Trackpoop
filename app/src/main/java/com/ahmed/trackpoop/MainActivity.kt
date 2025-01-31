package com.ahmed.trackpoop

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.ahmed.trackpoop.data.preferences.PreferencesManager
import com.ahmed.trackpoop.data.remote.PoopApiService
import com.ahmed.trackpoop.data.remote.UserApiService // Assuming there's a UserApiService
import com.ahmed.trackpoop.data.repository.PoopRepositoryImpl
import com.ahmed.trackpoop.data.repository.UserRepositoryImpl
import com.ahmed.trackpoop.di.BASE_URL
import com.ahmed.trackpoop.domain.repository.PoopRepository
import com.ahmed.trackpoop.domain.repository.UserRepository
import com.ahmed.trackpoop.navigation.Navigation
import com.ahmed.trackpoop.ui.theme.TrackPoopTheme
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize PreferencesManager
        val preferencesManager = PreferencesManager(
            sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        )

        // Initialize UserApiService (using Retrofit or any appropriate method)

        WindowCompat.setDecorFitsSystemWindows(window, true)
        enableEdgeToEdge()

        setContent {
            TrackPoopTheme {
                Scaffold(
                    containerColor = Color.Black,
                    content = { paddingValues ->
                        Navigation(
                            paddingValues = paddingValues,
                            preferencesManager = preferencesManager

                        )
                    }
                )
            }
        }
    }
}
