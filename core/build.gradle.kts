@file:Suppress("UnstableApiUsage")


plugins {
    id(Plugins.AppConfig.LIBRARY)
    id(Plugins.AppConfig.ANDROID)
}

android {
    namespace = "com.example.core"
    compileSdk = Dependencies.Versions.Core.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.Versions.Core.MIN_SDK

        testInstrumentationRunner = Dependencies.Config.TEST_INSTRUMENTATION_RUNNER
        consumerProguardFiles(Dependencies.Config.PROGUARG_RULES)
    }

    buildTypes {
        release {
            isMinifyEnabled = Dependencies.Config.IS_MINIFY_ENABLED
            proguardFiles(
                getDefaultProguardFile(Dependencies.Config.PRODUARG_FILE),
                Dependencies.Config.PROGUARG_RULES
            )
        }
    }

    buildFeatures {
        viewBinding = Dependencies.Other.VIEW_BINDING
        compose = true
    }

    compileOptions {
        sourceCompatibility = Dependencies.Config.SOURCE_COMPATIBILITY
        targetCompatibility = Dependencies.Config.TARGET_COMPATIBILITY
    }

    kotlinOptions {
        jvmTarget = Dependencies.Config.JVM_TARGET
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.UI.Compose.COMPILER
    }
}

dependencies {
    Dependencies.Core.apply {
        implementation(KTX)
    }

    Dependencies.UI.apply {
        implementation(NAVIGATION)
        implementation(NAVIGATION_UI)
        implementation(MATERIAL)
        implementation(LIVE_DATA)
        implementation(CONSTRAINT)
    }

    Dependencies.UI.Compose.apply {
        val composeBom = platform(BOM)
        implementation(composeBom)
        androidTestImplementation(composeBom)
        implementation(LIVE_DATA)
        implementation(MATERIAL3)
        implementation(MATERIAL)
        implementation(VIEW_MODEL)
        implementation(CONSTRAINT)
        implementation(FRAMEWORK)
        implementation(FOUNDATION)
        implementation(UI)
        implementation(RUNTIME)
        debugImplementation(PREVIEW)
        debugImplementation(NAVIGATION)
    }
}