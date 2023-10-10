package com.lukmadev.apps.weather.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lukmadev.apps.weather.ui.ProvideAppCompositionLocals
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.uikit.layout.SimpleScaffold
import com.lukmadev.uikit.navigation.ComponentNavGraph
import com.lukmadev.uikit.navigation.build

fun ComposeContentTestRule.setScreen(component: ComponentNavGraph) {
    setContent {
        val snackbarHostState = remember { SnackbarHostState() }

        WeatherTheme {
            ProvideAppCompositionLocals(snackbarHostState) {
                SimpleScaffold(
                    snackbarHostState = snackbarHostState,
                    content = { paddingValues ->
                        val navController = rememberNavController()
                        NavHost(
                            navController = navController,
                            startDestination = component.route,
                            modifier = Modifier.padding(paddingValues),
                        ) {
                            component.build(this)
                        }
                    },
                )
            }
        }
    }
}
