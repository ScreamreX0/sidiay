import org.gradle.api.JavaVersion

object Dependencies {
    object Modules {
        const val domain = ":domain"
        const val data = ":data"
        const val core = ":core"
        const val signIn = ":signin"
        const val app = ":app"
        const val home = ":home"
        const val notifications = ":notifications"
        const val scanner = ":scanner"
        const val settings = ":settings"
        const val mainMenu = ":main-menu"
    }

    object AppConfig {
        const val applicationId = "com.example.sidiay"
        const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val sourceCompatibility = JavaVersion.VERSION_11
        val targetCompatibility = JavaVersion.VERSION_11
        const val jvmTarget = "11"
        const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
        const val proguardFile = "proguard-android-optimize.txt"
        const val proguardRules = "proguard-rules.pro"
        const val isMinifyEnabled = false
    }

    object Core {
        const val ktx = "androidx.core:core-ktx:${Versions.ktx}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    }

    object Kapt {
        const val generateStubs = true
        const val correctErrorTypes = true
        const val kapt = "org.jetbrains.kotlinx:kotlinx-metadata-jvm:${Versions.kapt}"
    }

    object DI {
        const val hilt = "com.google.dagger:hilt-android:${Versions.di}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Versions.di}"
    }

    object Network {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    }

    object Multithreading {
        const val coroutines =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Test {
        const val junit = "androidx.test.ext:junit-ktx:${Versions.junit}"
        const val jupiter = "org.junit.jupiter:junit-jupiter"
        const val androidJunit = "junit:junit:${Versions.androidJunit}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    }

    object Compose {
        val bom = "androidx.compose:compose-bom:2022.12.00"
        val material = "androidx.compose.material3:material3"
        val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:2.5.1"
        val liveData = "androidx.compose.runtime:runtime-livedata"
        val constraint = "androidx.constraintlayout:constraintlayout-compose:1.0.1"
        val preview = "androidx.compose.ui:ui-tooling:1.1.1"
    }

    object Other {
        const val viewBinding = true
        const val safeArgs = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
        const val material = "com.google.android.material:material:${Versions.material}"
        const val constraint = "androidx.constraintlayout:constraintlayout:${Versions.constraint}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
        const val navigation = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    }

    object Versions {
        // CORE
        const val minSdk = 24
        const val targetSdk = 33
        const val compileSdk = 33
        const val versionCode = 1
        const val versionName = "1.0.0"
        const val buildToolsVersion = "29.0.3"
        const val lifecycle = "2.6.0-alpha03"
        const val material = "1.7.0"
        const val kapt = "0.5.0"
        const val constraint = "2.1.4"
        const val appcompat = "1.5.1"
        const val ktx = "1.9.0"
        const val gradle = "4.0.2"

        // DI
        const val di = "2.44"
        const val fragment = "1.5.5"

        // NAVIGATION
        const val navigation = "2.5.3"

        // TESTING
        const val junit = "1.1.5"
        const val androidJunit = "4.12"
        const val espresso = "3.5.0"

        // NETWORK
        const val retrofit = "2.9.0"
        const val okhttp = "4.5.0"

        // MULTITHREADING
        const val coroutines = "1.6.1"

        // COMPOSE
        const val compose = "1.3.2"
    }
}

