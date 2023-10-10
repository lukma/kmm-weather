package com.lukmadev.apps.weather.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lukmadev.apps.weather.feature.forecast.ForecastContainer
import com.lukmadev.apps.weather.feature.home.HomeContainer
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.uikit.layout.SimpleScaffold
import com.lukmadev.uikit.navigation.build

@Composable
fun WeatherApp(
    modifier: Modifier = Modifier,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    ProvideAppCompositionLocals(snackbarHostState) {
        WeatherTheme {
            SimpleScaffold(
                snackbarHostState = snackbarHostState,
                modifier = modifier,
                content = { paddingValues ->
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = HomeContainer.route,
                        modifier = Modifier.padding(paddingValues),
                    ) {
                        HomeContainer.build(this)
                        ForecastContainer.build(this)
                    }
                },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherTheme {
        WeatherApp()
    }
}
