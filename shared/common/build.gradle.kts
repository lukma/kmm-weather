plugins {
    id("lukmadev.multiplatform.library")
    id("lukmadev.multiplatform.android")
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.nativecoroutines)
    alias(libs.plugins.sqldelight)
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "common"
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.ExperimentalObjCName")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.kotlinx.datetime)
                implementation(libs.koin.core)
                implementation(libs.bundles.sqldelight.common)
                implementation(libs.bundles.ktor.common)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(libs.kotlin.test)
                implementation(libs.kotlinx.coroutines.test)
                implementation(libs.mockk.common)
                implementation(libs.koin.test)
                implementation(libs.ktor.mock)
            }
        }
        val androidMain by getting {
            dependencies {
                implementation(libs.koin.android)
                implementation(libs.sqldelight.android)
                implementation(libs.ktor.okhttp)
            }
        }
        val androidUnitTest by getting {
            dependencies {
                implementation(libs.mockk.core)
                implementation(libs.sqldelight.sqlite)
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation(libs.sqldelight.native)
                implementation(libs.ktor.darwin)
            }
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("com.lukmadev.core.data.db")
            dialect(libs.sqldelight.dialect.get().toString())
            generateAsync.set(true)
        }
    }
}

android {
    namespace = "com.lukmadev.core"
}
