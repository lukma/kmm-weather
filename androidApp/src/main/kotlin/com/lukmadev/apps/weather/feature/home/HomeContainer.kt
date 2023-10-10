package com.lukmadev.apps.weather.feature.home

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavBackStackEntry
import com.lukmadev.uikit.navigation.ComponentNavGraph
import org.koin.androidx.compose.koinViewModel

object HomeContainer : ComponentNavGraph {
    override val route: String = "home"
    override fun content(): @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = {
        val viewModel: HomeViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.sendEvent(HomeUiEvent.ShowFavoriteCities)
        }

        HomeView(uiState = uiState, onSendEvent = viewModel::sendEvent)
    }
}
