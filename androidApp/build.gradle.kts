import org.jetbrains.kotlin.konan.properties.Properties
import java.io.FileInputStream

plugins {
    id("lukmadev.android.application")
    id("lukmadev.android.application.compose")
}

android {
    namespace = "com.lukmadev.apps.weather"

    defaultConfig {
        applicationId = "com.lukmadev.apps.weather"
        versionCode = 1
        versionName = "1.0.0-alpha01"
    }

    @Suppress("UnstableApiUsage")
    testOptions {
        val skipTaskRegex = "^test(.*)ReleaseUnitTest$".toRegex()
        unitTests.all {
            it.extensions.configure(kotlinx.kover.api.KoverTaskExtension::class) {
                isDisabled.set(it.name.contains(skipTaskRegex))
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    flavorDimensions += "environment"
    productFlavors {
        val apiProp = Properties().apply {
            load(FileInputStream(file("../config/apis.properties")))
        }

        create("development") {
            dimension = "environment"
            applicationIdSuffix = ".dev"

            buildConfigField(
                "String",
                "WEATHER_API_HOST",
                "\"${apiProp.getProperty("DEVELOPMENT_WEATHER_API_HOST")}\""
            )
            buildConfigField(
                "String",
                "WEATHER_API_PREFIX_PATH",
                "\"${apiProp.getProperty("DEVELOPMENT_WEATHER_API_PREFIX_PATH")}\""
            )
        }
    }
}

dependencies {
    // Shared
    implementation(project(":shared:common"))
    implementation(project(":shared:uikit"))

    // Kotlin
    implementation(libs.kotlinx.datetime)

    // AndroidX
    implementation(libs.androidx.startup)
    implementation(libs.lifecycle.runtime)

    // Compose
    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.constraint.compose)
    implementation(libs.material3.compose)
    implementation(libs.material.compose.icon)
    implementation(libs.coil.compose)

    // Koin
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    // Accompanist
    implementation(libs.accompanist.placeholder)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.mockk.core)
    testImplementation(libs.koin.test)
    androidTestImplementation(libs.androidx.test.core)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.compose.ui.test.junit4)
    androidTestImplementation(libs.mockk.android)
    androidTestImplementation(libs.koin.test)
    debugImplementation(libs.compose.ui.test.tooling)
    debugImplementation(libs.compose.ui.test.manifest)
    debugImplementation(libs.androidx.tracing)
}
