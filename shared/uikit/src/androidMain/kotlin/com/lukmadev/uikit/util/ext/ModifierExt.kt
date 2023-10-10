@file:Suppress("DEPRECATION")

package com.lukmadev.uikit.util.ext

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

fun Modifier.shimmerPlaceholder(
    isLoading: Boolean,
    shape: Shape = RoundedCornerShape(size = 8.dp),
): Modifier = composed {
    val color = contentColorFor(MaterialTheme.colorScheme.surfaceVariant)
    placeholder(
        visible = isLoading,
        color = color.copy(alpha = .4f),
        shape = shape,
        highlight = PlaceholderHighlight.shimmer(color.copy(alpha = .5f)),
    )
}
