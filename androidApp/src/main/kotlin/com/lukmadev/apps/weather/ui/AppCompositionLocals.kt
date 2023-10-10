package com.lukmadev.apps.weather.ui

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import com.lukmadev.uikit.layout.ScaffoldController
import com.lukmadev.uikit.layout.ScaffoldControllerImpl

val LocalScaffoldController = compositionLocalOf<ScaffoldController> {
    error("CompositionLocal ScaffoldController not present")
}

@Composable
fun ProvideAppCompositionLocals(
    snackbarHostState: SnackbarHostState,
    content: @Composable () -> Unit
) {
    val scaffoldController = remember { ScaffoldControllerImpl(snackbarHostState) }

    CompositionLocalProvider(
        LocalScaffoldController provides scaffoldController,
    ) {
        content()
    }
}
