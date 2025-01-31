package com.ahmed.trackpoop.presentation.main.gemini

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.net.URLEncoder

class GeminiChatViewModel : ViewModel() {

    val messageList by lazy {
        mutableStateListOf<GeminiChatState>()
    }

    val plantSuggestions = mutableStateListOf<String>()  // List of plant name suggestions
    val plantImageUrls = mutableStateListOf<String>()    // List of plant image URLs


    val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = Constants.API_KEY
    )

    val agricultureKeywords = listOf(
        "yellowing leaves", "wilting", "root rot", "fungal infection", "pest", "disease",
        "blight", "chlorosis", "leaf spot", "powdery mildew", "aphids", "rust", "scale insects",
        "bacterial infection", "fungus", "watering issues", "nutrient deficiency", "phytophthora"
    )

    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                val chat = generativeModel.startChat(
                    history = messageList.map {
                        content(it.role) { text(it.message) }
                    }
                )

                messageList.add(GeminiChatState(question, "user"))
                messageList.add(GeminiChatState("Typing....", "model"))

                val response = chat.sendMessage(
                    "Based on the following symptoms, provide a diagnosis for the plant problem. " +
                            "Only give agricultural issues and no general health or other unrelated diagnoses. Symptoms: $question"
                )

                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }

                val filteredResponse = filterAgriculturalResponse(response.text.toString())

                if (filteredResponse.isEmpty()) {
                    messageList.add(
                        GeminiChatState(
                            "I only Respond on agricultural issues found.",
                            "model"
                        )
                    )
                } else {
                    filteredResponse.forEach { diagnosis ->
                        messageList.add(GeminiChatState(diagnosis, "model"))
                    }
                }
            } catch (e: Exception) {
                if (messageList.isNotEmpty()) {
                    messageList.removeAt(messageList.size - 1)
                }
                messageList.add(GeminiChatState("Error: ${e.message}", "model"))
                Log.e("ChatViewModel", "Error sending message", e)
            }
        }
    }

    private fun filterAgriculturalResponse(response: String): List<String> {
        val lines = response.split(",").map { it.trim() }
        return lines.filter { line ->
            agricultureKeywords.any { keyword -> line.contains(keyword, ignoreCase = true) }
        }
    }

    fun getPlantSuggestionsAndImages() {
        viewModelScope.launch {
            try {
                // Step 1: Get three plant names from Gemini
                val plantNames = withContext(Dispatchers.IO) {
                    val response = generativeModel.generateContent(
                        "Give me three plant names suitable for agricultural purposes, separated by commas."
                    )
                    response.text?.split(",")?.map { it.trim() } ?: emptyList()
                }

                if (plantNames.isEmpty()) {
                    Log.e("ChatViewModel", "No plant suggestions received from Gemini")
                    plantSuggestions.clear()
                    plantImageUrls.clear()
                    return@launch
                }

                // Update plant suggestions
                plantSuggestions.clear()
                plantSuggestions.addAll(plantNames)

                // Step 2: Fetch images for each plant name from Unsplash
                plantImageUrls.clear()
                for (plantName in plantNames) {
                    val imageUrl = withContext(Dispatchers.IO) {
                        fetchPlantImageFromUnsplash(plantName)
                    }
                    plantImageUrls.add(imageUrl)
                }

            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error fetching plant suggestions or images", e)
                plantSuggestions.clear()
                plantImageUrls.clear()
            }
        }
    }

    private suspend fun fetchPlantImageFromUnsplash(query: String): String {
        val encodedQuery = URLEncoder.encode(query, "UTF-8")
        val client = OkHttpClient()
        val url =
            "https://api.unsplash.com/search/photos?page=1&query=$encodedQuery&client_id=${Constants.UNSPLASH_API_KEY}"

        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                val response = client.newCall(request).execute()

                if (!response.isSuccessful) {
                    Log.e("ChatViewModel", "HTTP Error: ${response.code} - ${response.message}")
                    return@withContext ""
                }

                val responseBody = response.body?.string()
                val jsonObject = JSONObject(responseBody ?: "{}")
                val resultsArray = jsonObject.getJSONArray("results")

                if (resultsArray.length() > 0) {
                    resultsArray.getJSONObject(0)
                        .getJSONObject("urls")
                        .getString("regular")
                } else {
                    ""
                }
            } catch (e: Exception) {
                Log.e("ChatViewModel", "Error fetching image from Unsplash", e)
                ""
            }
        }
    }
}
