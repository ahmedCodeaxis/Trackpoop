package com.ahmed.trackpoop.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

fun uriToFile(uri: Uri, context: Context): File? {
    return try {
        // Handle different Android versions
        val contentResolver: ContentResolver = context.contentResolver

        // For Android 10 (API level 29) and above, Scoped Storage is used, so we need a different approach
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val tempFile = createTempFile(context)
            val outputStream: OutputStream = FileOutputStream(tempFile)

            // Copy the input stream to the output file
            inputStream?.copyTo(outputStream)
            outputStream.close()

            tempFile
        } else {
            // For older Android versions, we can get the file path directly from the MediaStore
            val cursor = contentResolver.query(uri, null, null, null, null)
            cursor?.use {
                if (it.moveToFirst()) {
                    val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATA)
                    if (columnIndex != -1) {
                        val filePath = it.getString(columnIndex)
                        return File(filePath)
                    }
                }
            }
            // If no valid file path is found, return a temporary file
            createTempFile(context)
        }
    } catch (e: Exception) {
        Log.e("PlantIdentify", "Failed to convert URI to file: ${e.message}")
        createTempFile(context) // Return a temporary file on error
    }
}



private fun createTempFile(context: Context): File {
    val tempFile = File(context.cacheDir, "temp_image_${System.currentTimeMillis()}.jpg")
    if (!tempFile.exists()) {
        tempFile.createNewFile()
    }
    return tempFile
}

