@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.application)
    id(Plugins.AppConfig.android)
    id(Plugins.Core.kapt)
    id(Plugins.Hilt.hilt) apply true
    id(Plugins.Core.parselize)
    id(Plugins.Core.safeArgs)
}

android {
    namespace = "com.example.sidiay"
    compileSdk = Dependencies.Versions.compileSdk

    defaultConfig {
        applicationId = Dependencies.AppConfig.applicationId
        minSdk = Dependencies.Versions.minSdk
        targetSdk = Dependencies.Versions.targetSdk
        versionCode = Dependencies.Versions.versionCode
        versionName = Dependencies.Versions.versionName

        testInstrumentationRunner = Dependencies.AppConfig.testInstrumentationRunner
    }

    buildTypes {
        release {
            isMinifyEnabled = Dependencies.AppConfig.isMinifyEnabled
            proguardFiles(
                getDefaultProguardFile(Dependencies.AppConfig.proguardFile),
                Dependencies.AppConfig.proguardRules
            )
        }
    }

    compileOptions {
        sourceCompatibility = Dependencies.AppConfig.sourceCompatibility
        targetCompatibility = Dependencies.AppConfig.targetCompatibility
    }

    kotlinOptions {
        jvmTarget = Dependencies.AppConfig.jvmTarget
    }

    kapt {
        generateStubs = Dependencies.Kapt.generateStubs
        correctErrorTypes = Dependencies.Kapt.correctErrorTypes
    }

    buildFeatures {
        viewBinding = Dependencies.Other.viewBinding
    }
}

dependencies {
    implementation(Dependencies.Core.ktx)
    implementation(Dependencies.Core.appCompat)
    kapt(Dependencies.Kapt.kapt)

    implementation(Dependencies.Test.junit)
    testImplementation(Dependencies.Test.jupiter)
    androidTestImplementation(Dependencies.Test.androidJunit)

    implementation(Dependencies.DI.hilt)
    kapt(Dependencies.DI.hiltCompiler)
    implementation(Dependencies.Core.fragment)

    implementation(Dependencies.Other.navigation)
    implementation(Dependencies.Other.navigationUI)
    implementation(Dependencies.Other.material)
    implementation(Dependencies.Other.liveData)
    implementation(Dependencies.Other.material)
    implementation(Dependencies.Other.constraint)

    implementation(Dependencies.Network.retrofit)
    implementation(Dependencies.Network.retrofitGson)

    implementation(Dependencies.Multithreading.coroutines)

    implementation(project(path = Dependencies.Modules.data))
    implementation(project(path = Dependencies.Modules.domain))
}