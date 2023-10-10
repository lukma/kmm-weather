package com.lukmadev.apps.weather.feature.forecast

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.lukmadev.uikit.navigation.ComponentNavGraph
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

object ForecastContainer : ComponentNavGraph {
    override val route: String = "forecast/{city}"

    override fun arguments(): List<NamedNavArgument> =
        listOf(navArgument("city") { type = NavType.StringType })

    override fun content(): @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit = {
        val viewModel: ForecastViewModel = koinViewModel {
            parametersOf(it.arguments?.getString("city"))
        }
        val uiState by viewModel.uiState.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.sendEvent(ForecastUiEvent.FetchDailyForecast)
        }

        ForecastView(uiState = uiState, onSendEvent = viewModel::sendEvent)
    }
}
