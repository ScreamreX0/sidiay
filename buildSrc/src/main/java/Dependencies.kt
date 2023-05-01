import org.gradle.api.JavaVersion

object Dependencies {
    object Config {
        const val APPLICATION_ID = "com.example.sidiay"
        const val TEST_INSTRUMENTATION_RUNNER = "androidx.test.runner.AndroidJUnitRunner"
        val SOURCE_COMPATIBILITY = JavaVersion.VERSION_11
        val TARGET_COMPATIBILITY = JavaVersion.VERSION_11
        const val JVM_TARGET = Versions.Core.JVM
        const val GRADLE = "com.android.tools.build:gradle:${Versions.Core.GRADLE}"
        const val PRODUARG_FILE = "proguard-android-optimize.txt"
        const val PROGUARG_RULES = "proguard-rules.pro"
        const val IS_MINIFY_ENABLED = false
    }

    object Core {
        const val KTX = "androidx.core:core-ktx:${Versions.Core.KTX}"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:${Versions.DI.DI_FRAGMENT}"
        const val APP_COMPAT = "androidx.appcompat:appcompat:${Versions.Core.APP_COMPAT}"
    }

    object Kapt {
        const val GENERATE_STUBS = true
        const val CORRECT_ERROR_TYPES = true
        const val KAPT = "org.jetbrains.kotlinx:kotlinx-metadata-jvm:${Versions.Core.KAPT}"
    }

    object DI {
        const val HILT = "com.google.dagger:hilt-android:${Versions.DI.VERSION}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Versions.DI.VERSION}"
        const val NAVIGATION = "androidx.hilt:hilt-navigation-compose:${Versions.DI.NAVIGATION}"
    }

    object Network {
        const val RETROFIT = "com.squareup.retrofit2:retrofit:${Versions.Network.RETROFIT}"
        const val RETROFIT_GSON = "com.squareup.retrofit2:converter-gson:${Versions.Network.RETROFIT}"
        const val LOGGER = "com.squareup.okhttp3:logging-interceptor:${Versions.Network.LOGGER}"
    }

    object Multithreading {
        const val COROUTINES = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.Multithreading.COROUTINES}"
    }

    object Test {
        const val JUNIT = "androidx.test.ext:junit-ktx:${Versions.Testing.JUNIT}"
        const val ANDROID_JUNIT = "junit:junit:${Versions.Testing.ANDROID_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Versions.Testing.ESPRESSO}"
    }

    object UI {
        const val MATERIAL = "com.google.android.material:material:${Versions.UI.MATERIAL}"
        const val CONSTRAINT = "androidx.constraintlayout:constraintlayout:${Versions.UI.CONSTRAINT}"
        const val LIVE_DATA = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Core.LIFECYCLE}"
        const val NAVIGATION = "androidx.navigation:navigation-fragment-ktx:${Versions.UI.NAVIGATION}"
        const val NAVIGATION_UI = "androidx.navigation:navigation-ui-ktx:${Versions.UI.NAVIGATION}"
        const val PAGINATION = "androidx.paging:paging-runtime:${Versions.UI.PAGINATION}"
        object Compose {
            const val BOM = "androidx.compose:compose-bom:2022.12.00"
            const val LIVE_DATA = "androidx.compose.runtime:runtime-livedata:${Versions.UI.Compose.FRAMEWORK}"
            const val MATERIAL3 = "androidx.compose.material3:material3:${Versions.UI.Compose.MATERIAL3}"
            const val FRAMEWORK = "androidx.ui:ui-framework:${Versions.UI.Compose.FRAMEWORK}"
            const val MATERIAL = "androidx.compose.material:material:${Versions.UI.Compose.MATERIAL}"
            const val VIEW_MODEL = "androidx.lifecycle:lifecycle-viewmodel-compose:${Versions.UI.Compose.VIEW_MODEL}"
            const val CONSTRAINT = "androidx.constraintlayout:constraintlayout-compose:${Versions.UI.Compose.CONSTRAINT}"
            const val PREVIEW = "androidx.compose.ui:ui-tooling:${Versions.UI.Compose.FRAMEWORK}"
            const val NAVIGATION = "androidx.navigation:navigation-compose:${Versions.UI.NAVIGATION}"
            const val ACTIVITY = "androidx.activity:activity-compose:${Versions.UI.Compose.ACTIVITY}"
            const val FOUNDATION = "androidx.compose.foundation:foundation:${Versions.UI.Compose.FOUNDATION}"
            const val RUNTIME = "androidx.compose.runtime:runtime:${Versions.UI.Compose.RUNTIME}"
            const val UI = "androidx.compose.ui:ui:${Versions.UI.Compose.UI}"
            const val PAGINATION = "androidx.paging:paging-compose:${Versions.UI.Compose.PAGINATION}"
            const val TAB_PAGING = "com.google.accompanist:accompanist-pager:${Versions.UI.Compose.ACCOMPANIST}"
            const val TAB_PAGING_INDICATORS = "com.google.accompanist:accompanist-pager-indicators:${Versions.UI.Compose.ACCOMPANIST}"
            const val FLOW_LAYOUTS = "com.google.accompanist:accompanist-flowlayout:${Versions.UI.Compose.ACCOMPANIST}"
        }
    }

    object DataStore {
        const val DATA_STORE = "androidx.datastore:datastore-preferences:${Versions.DataStore.DATA_STORE}"
    }

    object Other {
        const val VIEW_BINDING = true
        const val SAFE_ARGS = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.UI.NAVIGATION}"
        const val DATA_STORE = "androidx.datastore:datastore-preferences:${Versions.DataStore.DATA_STORE}"
    }

    object Modules {
        const val DOMAIN =  ":domain"
        const val DATA = ":data"
        const val CORE = ":core"
        const val SIGN_IN = ":signin"
        const val APP = ":app"
        const val HOME = ":home"
        const val NOTIFICATIONS = ":notifications"
        const val SCANNER = ":scanner"
        const val SETTINGS = ":settings"
        const val MAIN_MENU = ":main-menu"
    }
    object Versions {
        object Core {
            const val MIN_SDK = 26
            const val TARGET_SDK = 33
            const val COMPILE_SDK = 33
            const val VERSION_CODE = 1
            const val VERSION_NAME = "1.0.0"
            const val BUILD_TOOLS_VERSION = "29.0.3"
            const val KAPT = "0.5.0"
            const val APP_COMPAT = "1.5.1"
            const val KTX = "1.9.0"
            const val GRADLE = "8.0.0"
            const val LIFECYCLE = "2.6.0-alpha03"
            const val JVM = "11"
        }

        object DI {
            const val VERSION = "2.44"
            const val DI_FRAGMENT = "1.5.5"
            const val NAVIGATION = "1.0.0-alpha03"
        }

        object Testing {
            const val JUNIT = "1.1.5"
            const val ANDROID_JUNIT = "4.12"
            const val ESPRESSO = "3.5.0"
        }

        object Network {
            const val RETROFIT = "2.9.0"
            const val OKHTTP = "4.5.0"
            const val LOGGER = "4.10.0"
        }

        object Multithreading {
            const val COROUTINES = "1.6.1"
        }

        object UI {
            const val MATERIAL = "1.7.0"
            const val NAVIGATION = "2.5.3"
            const val CONSTRAINT = "2.1.4"
            const val PAGINATION = "3.1.1"
            object Compose {
                const val COMPILER = "1.3.2"
                const val FRAMEWORK = "0.1.0-dev03"
                const val NAVIGATION = "2.5.3"
                const val VIEW_MODEL = "2.5.1"
                const val CONSTRAINT = "1.0.1"
                const val PREVIEW = "1.1.1"
                const val ACTIVITY = "1.5.1"
                const val MATERIAL = "1.3.1"
                const val MATERIAL3 = "1.0.1"
                const val FOUNDATION = "1.3.1"
                const val RUNTIME = "1.3.3"
                const val UI = "1.3.3"
                const val PAGINATION = "1.0.0-alpha18"
                const val ACCOMPANIST = "0.28.0"
            }
        }

        object DataStore {
            const val DATA_STORE = "1.0.0"
            const val JAVALITE = "3.21.5"
            const val KOTLINLITE = "3.21.5"
        }
    }
}

