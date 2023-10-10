package com.lukmadev.buildlogic

import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import java.util.Optional

internal fun DependencyHandlerScope.detektPlugins(dependencyNotation: Optional<Provider<MinimalExternalModuleDependency>>) {
    add("detektPlugins", dependencyNotation.get().get())
}