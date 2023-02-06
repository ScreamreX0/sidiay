buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath(Dependencies.AppConfig.gradle)
        classpath(Dependencies.Other.safeArgs)
    }
}

plugins {
    id("com.android.library") version "7.4.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.0" apply false
    id(Plugins.AppConfig.jvm) version Plugins.Versions.jvm apply false
}