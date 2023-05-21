@file:Suppress("UnstableApiUsage")

plugins {
    id(Plugins.AppConfig.LIBRARY)
    id(Plugins.AppConfig.ANDROID)
    id(Plugins.Core.PARSELIZE)
}

android {
    namespace = "com.example.data"
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
}

dependencies {
    Dependencies.Core.apply {
        implementation(FRAGMENT)
        implementation(KTX)
    }
    Dependencies.Multithreading.apply {
        implementation(COROUTINES)
    }
    Dependencies.DI.apply {
        implementation(HILT)
    }
    Dependencies.Network.apply {
        implementation(RETROFIT)
        implementation(RETROFIT_GSON)
    }
    Dependencies.DataStore.apply {
        implementation(DATA_STORE)
    }
    Dependencies.Test.apply {
        implementation(JUNIT)
        implementation(ANDROID_JUNIT)
    }
    Dependencies.Firebase.apply {
        implementation(MESSAGING)
    }
    Dependencies.Modules.apply {
        implementation(project(path = DOMAIN))
        implementation(project(path = CORE))
    }
}