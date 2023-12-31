package com.lukmadev.uikit.navigation

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

interface ComponentNavGraph {
    val route: String
    fun arguments(): List<NamedNavArgument> = emptyList()
    fun deepLinks(): List<NavDeepLink> = emptyList()
    fun content(): @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
}

fun ComponentNavGraph.build(builder: NavGraphBuilder) {
    val navGraph: NavGraphBuilder.() -> Unit = {
        composable(
            route = this@build.route,
            arguments = this@build.arguments(),
            deepLinks = this@build.deepLinks(),
            content = this@build.content(),
        )
    }
    navGraph(builder)
}
