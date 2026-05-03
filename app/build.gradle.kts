plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "me.vikas.newsapp"
    compileSdk {
        version = release(36) {
            minorApiLevel = 1
        }
    }
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "me.vikas.newsapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String", "BASE_URL", "\"https://newsapi.org/\"")

        buildConfigField(
            "String", "API_KEY", "\"c1f1b06b17484996972e3dec2be6abdf\"")

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro"
            )
        }
    }

    buildFeatures {
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
}

dependencies {
    // UI and Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.material)


    // Compose Bom
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)

    // Material Icons Extended ✅
    implementation(libs.androidx.material.icons.extended)


    // ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Navigation Compose
    // Navigation
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.hilt.navigation.compose)

    // Networking (Retrofit)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // Log Interceptor
    implementation(libs.logging.interceptor)

    // Image Loading (Coil)
    implementation(libs.coil.compose)
    implementation(libs.coil.network.okhttp)

    // Lifecycle (Data Manipulation)
    implementation(libs.androidx.lifecycle.runtime)
    implementation(libs.androidx.lifecycle.viewmodel)

    // Room (Offline-First Storage)
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler) // Use KSP for Kotlin 2.2.10

    // Coroutines (Asynchronous Programming)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android) // Required for Dispatchers.Main

    // Dagger-Hilt
    implementation(libs.hilt.android)
//    implementation(libs.androidx.hilt.common)
    ksp(libs.hilt.compiler)

    implementation(libs.androidx.hilt.work)
    ksp(libs.androidx.hilt.compiler)

    //Custom Tab Browser
    implementation(libs.androidx.browser.v190)

    //Paging
    implementation(libs.paging.runtime.ktx)
    implementation(libs.paging.compose)
    implementation(libs.androidx.paging.common)  // This is important

    // Work Manager
    implementation(libs.androidx.work.runtime.ktx)

    //Unit Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test) // For testing Suspend functions
    testImplementation(libs.mockito.core)
    testImplementation(libs.androidx.core.testing)
    testImplementation(libs.turbine)

    // Instrumentation Testing
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    // Compose Testing Dependencies
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}