import com.lukmadev.buildlogic.configureDetekt
import com.lukmadev.buildlogic.detektConfigDir
import com.lukmadev.buildlogic.detektPlugins
import com.lukmadev.buildlogic.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class MultiplatformLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("org.jetbrains.kotlin.multiplatform")
                apply("io.gitlab.arturbosch.detekt")
            }

            configureDetekt {
                config.setFrom(files("${rootProject.detektConfigDir}/detekt-multiplatform.yml"))
                source.setFrom(
                    files(
                        "src/androidMain",
                        "src/commonMain",
                        "src/iosMain",
                    )
                )
            }

            dependencies {
                detektPlugins(libs.findLibrary("detekt.rules.libraries"))
                detektPlugins(libs.findLibrary("detekt.rules.compose"))
            }
        }
    }
}
