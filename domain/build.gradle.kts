@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.library)
    id(Plugins.AppConfig.android)
    id(Plugins.Core.parselize)
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

    Dependencies.Test.apply {
        androidTestImplementation(ANDROID_JUNIT)
        androidTestImplementation(ESPRESSO)
        testImplementation(JUNIT)
    }

    Dependencies.Modules.apply {
        implementation(project(path = CORE))
    }
}