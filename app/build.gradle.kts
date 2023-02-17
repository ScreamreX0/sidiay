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
    compileSdk = Dependencies.Versions.Core.COMPILE_SDK

    defaultConfig {
        applicationId = Dependencies.Config.APPLICATION_ID
        minSdk = Dependencies.Versions.Core.MIN_SDK
        targetSdk = Dependencies.Versions.Core.TARGET_SDK
        versionCode = Dependencies.Versions.Core.VERSION_CODE
        versionName = Dependencies.Versions.Core.VERSION_NAME
        testInstrumentationRunner = Dependencies.Config.TEST_INSTRUMENTATION_RUNNER
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

    kapt {
        generateStubs = Dependencies.Kapt.GENERATE_STUBS
        correctErrorTypes = Dependencies.Kapt.CORRECT_ERROR_TYPES
    }

    buildFeatures {
        viewBinding = Dependencies.Other.VIEW_BINDING
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Dependencies.Versions.UI.Compose.VERSION
    }
}

dependencies {
    Dependencies.Core.apply {
        implementation(KTX)
        implementation(APP_COMPAT)
        implementation(FRAGMENT)
    }

    kapt(Dependencies.Kapt.KAPT)

    Dependencies.DI.apply {
        implementation(HILT)
        kapt(HILT_COMPILER)
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
        implementation(FRAMEWORK)
        implementation(NAVIGATION)
        implementation(ACTIVITY)
    }

    Dependencies.Network.apply {
        implementation(RETROFIT)
        implementation(RETROFIT_GSON)
    }

    Dependencies.Modules.apply {
        implementation(project(path = DATA))
        implementation(project(path = DOMAIN))
        implementation(project(path = CORE))
        implementation(project(path = SIGN_IN))
        implementation(project(path = HOME))
        implementation(project(path = NOTIFICATIONS))
        implementation(project(path = SCANNER))
        implementation(project(path = SETTINGS))
        implementation(project(path = MAIN_MENU))
    }
}