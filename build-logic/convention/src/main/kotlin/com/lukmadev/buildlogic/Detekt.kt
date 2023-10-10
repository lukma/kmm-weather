package com.lukmadev.buildlogic

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureDetekt(block: DetektExtension.() -> Unit) {
    tasks.withType(Detekt::class.java) {
        reports {
            xml.required.set(false)
            html.required.set(true)
            txt.required.set(false)
            sarif.required.set(true)
            md.required.set(false)
        }
    }

    extensions.configure<DetektExtension>("detekt") {
        apply(block)
    }

    dependencies {
        detektPlugins(libs.findLibrary("detekt.formatting"))
        detektPlugins(libs.findLibrary("detekt.rules.ruleauthors"))
    }
}

internal inline val Project.detektConfigDir: String
    get() = "${projectDir}/config/detekt/"
