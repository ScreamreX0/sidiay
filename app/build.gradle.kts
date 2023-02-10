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
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.compose
    }
}

dependencies {
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
    }

    Dependencies.Modules.apply {
        implementation(project(path = data))
        implementation(project(path = domain))
        implementation(project(path = core))
        implementation(project(path = signIn))
        implementation(project(path = home))
        implementation(project(path = notifications))
        implementation(project(path = scanner))
        implementation(project(path = settings))
        implementation(project(path = mainMenu))
    }
}