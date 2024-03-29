@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.LIBRARY)
    id(Plugins.AppConfig.ANDROID)
    id(Plugins.Core.KAPT)
    id(Plugins.Hilt.HILT) apply true
    id(Plugins.Core.PARSELIZE)
    id(Plugins.Core.SAVEARGS)
}

android {
    namespace = "com.example.settings"
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
        implementation(APP_COMPAT)
        implementation(FRAGMENT)
    }
    kapt(Dependencies.Kapt.KAPT)

    Dependencies.Test.apply {
        implementation(JUNIT)
        androidTestImplementation(ANDROID_JUNIT)
    }

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

    Dependencies.Network.apply {
        implementation(RETROFIT)
        implementation(RETROFIT_GSON)
    }

    Dependencies.Multithreading.apply {
        implementation(COROUTINES)
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
        implementation(FOUNDATION)
        implementation(FRAMEWORK)
        implementation(UI)
        implementation(RUNTIME)
        implementation(TAB_PAGING)
        implementation(FLOW_LAYOUTS)
        debugImplementation(PREVIEW)
        debugImplementation(NAVIGATION)
    }

    Dependencies.Modules.apply {
        implementation(project(path = DATA))
        implementation(project(path = DOMAIN))
        implementation(project(path = CORE))
    }
}