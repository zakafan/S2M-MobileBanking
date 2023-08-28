plugins {
    id("com.android.application")
    kotlin("android")
    id ("com.google.relay") version "0.3.00"
}

android {
    namespace = "com.example.s2m.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.s2m.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation("com.google.android.gms:play-services-fitness:21.1.0")
    val camerax_version = "1.2.3"
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.3")
    implementation("androidx.compose.ui:ui-tooling:1.4.3")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.3")
    implementation("androidx.compose.foundation:foundation:1.4.3")
    implementation("androidx.compose.material:material:1.4.3")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation("androidx.compose.runtime:runtime:1.4.3")
    implementation ("com.google.android.material:material:1.9.0")


    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation ("androidx.navigation:navigation-compose:2.6.0")
    implementation ("com.google.accompanist:accompanist-pager:0.22.0-rc")
    implementation ("io.coil-kt:coil-compose:1.4.0")

    implementation ("com.google.maps.android:maps-compose:2.7.2")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:18.0.0")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.5.0")

    implementation("androidx.camera:camera-camera2:${camerax_version}")
    implementation("androidx.camera:camera-lifecycle:${camerax_version}")
    implementation("androidx.camera:camera-view:${camerax_version}")

    implementation ("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.himanshoe:charty:2.0.0-alpha01")
    implementation ("com.patrykandpatrick.vico:core:1.8.0")
    implementation ("com.patrykandpatrick.vico:compose:1.8.0")

    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")



}