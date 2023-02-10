@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
    id(Plugins.Core.kapt)
    id(Plugins.Hilt.hilt) apply true
    id(Plugins.Core.parselize)
    id(Plugins.Core.safeArgs)
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

    buildFeatures {
        viewBinding = Dependencies.Other.viewBinding
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.compose
    }
}

dependencies {
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.1")
    Dependencies.Core.apply {
        implementation(ktx)
        implementation(appCompat)
        implementation(fragment)
    }

    kapt(Dependencies.Kapt.kapt)

    Dependencies.Test.apply {
        implementation(junit)
        testImplementation(jupiter)
        androidTestImplementation(androidJunit)
    }

    Dependencies.DI.apply {
        implementation(hilt)
        kapt(hiltCompiler)
    }

    Dependencies.Other.apply {
        implementation(navigation)
        implementation(navigationUI)
        implementation(material)
        implementation(liveData)
        implementation(constraint)
    }

    Dependencies.Network.apply {
        implementation(retrofit)
        implementation(retrofitGson)
    }

    Dependencies.Multithreading.apply {
        implementation(coroutines)
    }

    Dependencies.Compose.apply {
        val composeBom = platform(bom)
        implementation(composeBom)
        androidTestImplementation(composeBom)
        implementation(liveData)
        implementation(material)
        implementation(viewModel)
        implementation(constraint)
        debugImplementation(preview)
    }


    implementation(project(path = Dependencies.Modules.data))
    implementation(project(path = Dependencies.Modules.domain))
    implementation(project(path = Dependencies.Modules.core))
    implementation(project(path = Dependencies.Modules.mainMenu))
}