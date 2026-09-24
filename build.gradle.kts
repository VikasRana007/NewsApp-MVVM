// Top-level build file where you can add configuration options common to all subprojects/modules.
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.hilt.android) apply false
    id("com.google.gms.google-services") version "4.5.0" apply false
    id("com.google.firebase.appdistribution") version "5.3.0" apply false // App Distribution Gradle Plugin
}