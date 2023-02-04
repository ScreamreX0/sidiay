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
    id(Plugins.AppConfig.application) version Plugins.Versions.application apply false
    id(Plugins.AppConfig.library) version Plugins.Versions.application apply false
    id(Plugins.AppConfig.android) version Plugins.Versions.android apply false
    id(Plugins.AppConfig.jvm) version Plugins.Versions.jvm apply false
}