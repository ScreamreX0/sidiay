@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.LIBRARY)
    id(Plugins.AppConfig.ANDROID)
    id(Plugins.Core.PARSELIZE)
}

android {
    namespace = "com.example.domain"
    compileSdk = Dependencies.Versions.Core.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.Versions.Core.MIN_SDK
        targetSdk = Dependencies.Versions.Core.TARGET_SDK

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
}

dependencies {
    Dependencies.Core.apply {
        implementation(KTX)
        implementation(APP_COMPAT)
        implementation(FRAGMENT)
    }

    Dependencies.DI.apply {
        implementation(HILT)
    }

    Dependencies.UI.apply {
        implementation(MATERIAL)
    }

    Dependencies.UI.Compose.apply {
        val composeBom = platform(BOM)
        implementation(composeBom)
        androidTestImplementation(composeBom)
        debugImplementation(NAVIGATION)
    }

    Dependencies.Network.apply {
        implementation(RETROFIT_GSON)
    }

    Dependencies.Test.apply {
        androidTestImplementation(ANDROID_JUNIT)
        androidTestImplementation(ESPRESSO)
        testImplementation(JUNIT)
    }

    Dependencies.Modules.apply {
        implementation(project(path = CORE))
    }

    Dependencies.Firebase.apply {
        implementation(GMS)
    }
}