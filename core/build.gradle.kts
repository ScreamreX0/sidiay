@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
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

    compileOptions {
        sourceCompatibility = Dependencies.Config.SOURCE_COMPATIBILITY
        targetCompatibility = Dependencies.Config.TARGET_COMPATIBILITY
    }

    kotlinOptions {
        jvmTarget = Dependencies.Config.JVM_TARGET
    }

    buildFeatures {
        viewBinding = Dependencies.Other.VIEW_BINDING
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
    }

    Dependencies.UI.Compose.apply {
        val composeBom = platform(BOM)
        implementation(composeBom)
        androidTestImplementation(composeBom)
        implementation(LIVE_DATA)
        implementation(MATERIAL)
        implementation(VIEW_MODEL)
        implementation(CONSTRAINT)
        debugImplementation(PREVIEW)
    }
}