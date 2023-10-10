package com.lukmadev.uikit.layout

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleScaffold(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        content = content
    )
}
