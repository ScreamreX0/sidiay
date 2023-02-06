@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
}

android {
    namespace = "com.example.core"
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

    buildFeatures {
        viewBinding = Dependencies.Other.viewBinding
    }
}

dependencies {

    implementation(Dependencies.Core.ktx)
    implementation(Dependencies.Other.navigation)
    implementation(Dependencies.Other.navigationUI)
    implementation(Dependencies.Other.material)
}