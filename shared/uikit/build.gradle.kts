plugins {
    id("lukmadev.multiplatform.library")
    id("lukmadev.multiplatform.android")
    id("lukmadev.android.library.compose")
}

kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":shared:common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui.core)
                implementation(libs.constraint.compose)
                implementation(libs.material3.compose)
                implementation(libs.navigation.compose)
                implementation(libs.accompanist.placeholder)
            }
        }
    }
}

android {
    namespace = "com.lukmadev.uikit"
}
