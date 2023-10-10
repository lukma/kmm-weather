import kotlinx.kover.api.KoverMergedConfig
import kotlinx.kover.api.KoverProjectConfig
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KoverConventionPlugin : Plugin<Project> {
    private val classesExcludes = listOf(
        "*BuildConfig",
        "*Activity",
        "*ActivityKt",
        "*Activity\$*",
        "*Initializer",
        "*InitializerKt",
        "*Initializer\$*",
        "*ComposableSingletons*",
        "*ConstraintLayout*",
        "*shimmerPlaceholder*",
        "*Container",
        "*ContainerKt",
        "*Container\$*",
        "*View",
        "*ViewKt",
        "*View\$*",
        "*.data.db.*",
        "*.ui.*",
    )
    private val annotationsExcludes = listOf(
        "androidx.compose.runtime.Composable",
    )
    private val verifyRuleBoundMinValue = 80

    override fun apply(target: Project) {
        with(target) {
            configureKoverMerged()
            subprojects {
                configureKoverSubProjects()
            }
        }
    }

    private fun Project.configureKoverSubProjects() {
        pluginManager.apply("org.jetbrains.kotlinx.kover")

        extensions.configure<KoverProjectConfig> {
            filters {
                classes {
                    excludes += classesExcludes
                }

                annotations {
                    excludes += annotationsExcludes
                }
            }

            verify {
                rule {
                    bound {
                        minValue = verifyRuleBoundMinValue
                    }
                }
            }
        }
    }

    private fun Project.configureKoverMerged() {
        pluginManager.apply("org.jetbrains.kotlinx.kover")

        extensions.configure<KoverMergedConfig> {
            enable()

            filters {
                classes {
                    excludes += classesExcludes
                }

                annotations {
                    excludes += annotationsExcludes
                }

                projects {
                    excludes += listOf(
                        ":shared:uikit",
                    )
                }
            }

            verify {
                rule {
                    bound {
                        minValue = verifyRuleBoundMinValue
                    }
                }
            }
        }
    }
}
