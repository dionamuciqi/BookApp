plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.bookapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.bookapp"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat) // AppCompat library for backward compatibility with older Android versions
    implementation(libs.material) // Material Components for UI elements like buttons, cards, etc.
    implementation(libs.activity) // Android Activity library for handling activities
    implementation(libs.constraintlayout) // ConstraintLayout for flexible and efficient layouts
    implementation(libs.firebase.analytics) // Firebase Analytics for tracking app usage
    implementation(libs.firebase.auth) // Firebase Authentication for user sign-in functionality
    implementation(libs.firebase.database) // Firebase Realtime Database for storing and syncing app data
    implementation(libs.firebase.storage) // Firebase Storage for file uploads and downloads

    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.ext.junit) // Android-specific JUnit tests
    androidTestImplementation(libs.espresso.core) // Espresso for UI testing
}

