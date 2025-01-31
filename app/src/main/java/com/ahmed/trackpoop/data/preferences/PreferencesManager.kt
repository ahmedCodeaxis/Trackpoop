package com.ahmed.trackpoop.data.preferences

import android.content.SharedPreferences
import android.util.Log

class PreferencesManager(private val sharedPreferences: SharedPreferences) {

    private val KEY_TOKEN = "key_token"
    private val KEY_REFRESH_TOKEN = "key_refresh_token"

    // Save user information and token expiration time
    fun saveUserInfo(token: String, refreshToken: String) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_REFRESH_TOKEN, refreshToken)

            .apply()

        // Log the saved token and refresh token for debugging purposes
        Log.d("Auth", "Token saved: $token")
        Log.d("Auth", "Refresh token saved: $refreshToken")
    }

    // Retrieve the user token if it's not expired
    fun getUserToken(): String? {
        val token = sharedPreferences.getString(KEY_TOKEN, null)
        Log.e("token retrieved is :: ",token.toString())
       return token
    }

    // Retrieve the refresh token
    fun getRefreshToken(): String? = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)

    // Update tokens when refreshed
    fun updateTokens(token: String, refreshToken: String, expirationTime: Long) {
        sharedPreferences.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            //.putLong(KEY_TOKEN_EXPIRATION, expirationTime)
            .apply()

        Log.d("Auth", "Tokens updated. Token: $token, Refresh token: $refreshToken")
    }

    // Clear the saved user information (tokens)
    fun clearUserInfo() {
        sharedPreferences.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_REFRESH_TOKEN)
            //.remove(KEY_TOKEN_EXPIRATION)
            .apply()

        Log.d("Auth", "User info cleared")
    }

    // Check if the user is logged in by verifying both tokens
    fun isLoggedIn(): Boolean {
        val token = getUserToken()
        return token != null && getRefreshToken() != null
    }
}
