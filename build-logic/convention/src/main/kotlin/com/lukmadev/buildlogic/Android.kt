package com.lukmadev.buildlogic

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Project

internal fun Project.configureAndroid(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    commonExtension.apply {
        compileSdk = 34

        defaultConfig {
            minSdk = 24
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        buildFeatures {
            buildConfig = true
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
}
