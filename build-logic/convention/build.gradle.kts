plugins {
    `kotlin-dsl`
}

group = "com.lukmadev.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlinx.kover.gradlePlugin)
    implementation(libs.detekt.gradlePlugin)
    implementation(libs.android.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("kover") {
            id = "lukmadev.kover"
            implementationClass = "KoverConventionPlugin"
        }
        register("multiplatformLibrary") {
            id = "lukmadev.multiplatform.library"
            implementationClass = "MultiplatformLibraryConventionPlugin"
        }
        register("multiplatformAndroidLibrary") {
            id = "lukmadev.multiplatform.android"
            implementationClass = "MultiplatformAndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = "lukmadev.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "lukmadev.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "lukmadev.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}
