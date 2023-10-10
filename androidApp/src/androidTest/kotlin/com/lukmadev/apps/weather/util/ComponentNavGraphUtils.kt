package com.lukmadev.apps.weather.util

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.lukmadev.apps.weather.ui.ProvideAppCompositionLocals
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.uikit.layout.SimpleScaffold
import com.lukmadev.uikit.navigation.ComponentNavGraph
import com.lukmadev.uikit.navigation.build
import kotlinx.coroutines.delay

fun ComposeContentTestRule.setScreen(
    withDummyStartDestination: Boolean = false,
    navigateTo: () -> String = { "" },
    component: ComponentNavGraph,
) {
    setContent {
        val snackbarHostState = remember { SnackbarHostState() }

        WeatherTheme {
            ProvideAppCompositionLocals(snackbarHostState) {
                SimpleScaffold(
                    snackbarHostState = snackbarHostState,
                    content = { paddingValues ->
                        val navController = rememberNavController()

                        if (withDummyStartDestination) {
                            LaunchedEffect(Unit) {
                                delay(10)
                                navController.navigate(navigateTo())
                            }
                        }

                        NavHost(
                            navController = navController,
                            startDestination = if (withDummyStartDestination) DummyView.route else component.route,
                            modifier = Modifier.padding(paddingValues),
                        ) {
                            if (withDummyStartDestination) {
                                DummyView.build(this)
                            }
                            component.build(this)
                        }
                    },
                )
            }
        }
    }
}

object DummyView : ComponentNavGraph {
    override val route: String = "dummy"
    override fun content(): @Composable (AnimatedContentScope.(NavBackStackEntry) -> Unit) = {}
}
