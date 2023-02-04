@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
    id(Plugins.Core.parselize)
}

android {
    namespace = "com.example.domain"
    compileSdk = Dependencies.Versions.compileSdk

    defaultConfig {
        minSdk = Dependencies.Versions.minSdk
        targetSdk = Dependencies.Versions.targetSdk

        testInstrumentationRunner = Dependencies.AppConfig.testInstrumentationRunner
        consumerProguardFiles(Dependencies.AppConfig.proguardRules)
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
}

dependencies {
    implementation(Dependencies.Core.ktx)
    implementation(Dependencies.Core.appCompat)
    implementation(Dependencies.Core.fragment)

    implementation(Dependencies.DI.hilt)

    implementation(Dependencies.Other.material)

    androidTestImplementation(Dependencies.Test.androidJunit)
    androidTestImplementation(Dependencies.Test.espresso)
    testImplementation(Dependencies.Test.jupiter)
    testImplementation(Dependencies.Test.junit)
}