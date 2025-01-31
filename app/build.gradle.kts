plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")

}

android {
    namespace = "com.ahmed.trackpoop"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.ahmed.trackpoop"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.testing)
    implementation(libs.androidx.room.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")
    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Koin DI
    implementation("io.insert-koin:koin-android:4.0.0")
    implementation("io.insert-koin:koin-core:4.0.0")
    implementation("io.insert-koin:koin-androidx-compose:4.0.0")
    implementation("io.insert-koin:koin-compose-viewmodel:4.0.0")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // Coroutine Lifecycle Scopes
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    // System ui controller
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.27.0")

    implementation("androidx.navigation:navigation-compose:2.5.3")

    implementation("androidx.compose.ui:ui:1.7.5")
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation("androidx.compose.material:material-icons-extended:1.7.5")
    implementation("androidx.compose.material:material:1.7.5")

    //Dependance pour AR
    implementation ("io.github.sceneview:arsceneview:0.10.0") // Dépendance pour ARSceneView
    implementation ("com.github.jitpack:example:1.1")
    implementation ("androidx.compose.foundation:foundation:1.5.2")

    // Dependance GridView AR
    implementation ("androidx.compose.foundation:foundation:1.4.0-alpha03")

    // implementation ("com.google.accompanist:accompanist-gesture:0.32.0")
    implementation ("io.coil-kt:coil-compose:2.1.0")

    //Gemini
    implementation("com.google.ai.client.generativeai:generativeai:0.4.0")

    //ASync image
    implementation("io.coil-kt:coil-compose:2.2.2")

    implementation ("androidx.compose.runtime:runtime:1.5.0") // Replace with the correct version

}


kapt {
    correctErrorTypes = true
}