@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
}

android {
    namespace = "com.example.signin"
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

    implementation(Dependencies.Other.material)
    
    testImplementation(Dependencies.Test.junit)
    androidTestImplementation(Dependencies.Test.espresso)
}