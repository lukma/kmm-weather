package com.lukmadev.apps.weather.feature.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import com.lukmadev.uikit.navigation.ComponentNavGraph
import org.koin.androidx.compose.koinViewModel

object HomeContainer : ComponentNavGraph {
    override val route: String = "home"
    override fun content(): @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = {
        val viewModel: HomeViewModel = koinViewModel()
    }
}
