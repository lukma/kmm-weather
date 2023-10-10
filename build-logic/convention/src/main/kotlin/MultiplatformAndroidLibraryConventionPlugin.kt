import com.android.build.gradle.LibraryExtension
import com.lukmadev.buildlogic.configureAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class MultiplatformAndroidLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("com.android.library")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                androidTarget()
            }

            extensions.configure<LibraryExtension> {
                configureAndroid(this)
                defaultConfig.targetSdk = 34
            }
        }
    }
}
