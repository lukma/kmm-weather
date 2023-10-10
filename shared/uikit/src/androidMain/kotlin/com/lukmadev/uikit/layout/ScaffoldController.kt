package com.lukmadev.uikit.layout

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult

interface ScaffoldController {
    suspend fun showSnackbar(
        message: String,
        actionLabel: String? = null,
        withDismissAction: Boolean = false,
        duration: SnackbarDuration = SnackbarDuration.Short,
    ): SnackbarResult
}

class ScaffoldControllerImpl(
    private val snackbarHostState: SnackbarHostState,
) : ScaffoldController {

    override suspend fun showSnackbar(
        message: String,
        actionLabel: String?,
        withDismissAction: Boolean,
        duration: SnackbarDuration,
    ): SnackbarResult {
        return snackbarHostState.showSnackbar(message, actionLabel, withDismissAction, duration)
    }
}
