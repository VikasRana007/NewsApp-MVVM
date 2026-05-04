plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.plugin.serialization")
    id("com.google.devtools.ksp")
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
        viewBinding = true
        dataBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
}

dependencies {
    // UI and Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.browser)

    // Networking (Retrofit)
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)

    // Log Interceptor
    implementation(libs.logging.interceptor)

    // Image Loading (Coil)
    implementation(libs.coil.kt)
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

    // Dagger 2
    implementation(libs.dagger.runtime)
    ksp(libs.dagger.compiler)

    //Custom Tab Browser
    implementation(libs.androidx.browser.v190)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test) // For testing Suspend functions
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}