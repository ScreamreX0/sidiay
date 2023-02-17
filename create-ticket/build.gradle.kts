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
    namespace = "com.example.create_ticket"
    compileSdk = Dependencies.Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Dependencies.Versions.MIN_SDK
        targetSdk = Dependencies.Versions.TARGET_SDK

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
    implementation(Dependencies.Core.KTX)
    implementation(Dependencies.Core.APP_COMPAT)
    implementation(Dependencies.Core.FRAGMENT)
    kapt(Dependencies.Kapt.KAPT)

    implementation(Dependencies.Test.JUNIT)
    testImplementation(Dependencies.Test.JUPITER)
    androidTestImplementation(Dependencies.Test.ANDROID_JUNIT)

    implementation(Dependencies.DI.HILT)
    kapt(Dependencies.DI.HILT_COMPILER)

    implementation(Dependencies.Other.NAVIGATION)
    implementation(Dependencies.Other.NAVIGATION_UI)
    implementation(Dependencies.Other.MATERIAL)
    implementation(Dependencies.Other.LIVE_DATA)
    implementation(Dependencies.Other.CONSTRAINT)

    implementation(Dependencies.Network.RETROFIT)
    implementation(Dependencies.Network.RETROFIT_GSON)

    implementation(Dependencies.Multithreading.COROUTINES)

    implementation(project(path = Dependencies.Modules.DATA))
    implementation(project(path = Dependencies.Modules.DOMAIN))
    implementation(project(path = Dependencies.Modules.CORE))
}