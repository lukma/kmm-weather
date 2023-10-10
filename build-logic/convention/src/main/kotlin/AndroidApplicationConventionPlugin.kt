import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.lukmadev.buildlogic.configureAndroid
import com.lukmadev.buildlogic.configureDetekt
import com.lukmadev.buildlogic.detektConfigDir
import com.lukmadev.buildlogic.detektPlugins
import com.lukmadev.buildlogic.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.ExtensionAware
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.android")
                apply("io.gitlab.arturbosch.detekt")
                apply("com.android.application")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureAndroid(this)
                defaultConfig.targetSdk = 34

                @Suppress("UnstableApiUsage")
                testOptions {
                    packaging {
                        jniLibs {
                            useLegacyPackaging = true
                        }
                    }
                }

                (this as ExtensionAware).extensions.configure<KotlinJvmOptions>("kotlinOptions") {
                    jvmTarget = JavaVersion.VERSION_17.toString()
                }

                packaging {
                    resources {
                        excludes += "/META-INF/{AL2.0,LGPL2.1,LICENSE.md,LICENSE-notice.md}"
                    }
                }
            }

            configureDetekt {
                config.setFrom(files("${rootProject.detektConfigDir}/detekt-android.yml"))
            }

            dependencies {
                detektPlugins(libs.findLibrary("detekt.rules.compose"))
            }
        }
    }
}
