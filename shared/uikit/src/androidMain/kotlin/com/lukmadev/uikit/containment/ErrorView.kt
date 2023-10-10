package com.lukmadev.uikit.containment

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukmadev.uikit.R

@Composable
fun ErrorView(
    error: Throwable,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(modifier) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 8.dp)
                .semantics {
                    testTag = "ErrorView"
                },
        ) {
            val (message, retryButton) = createRefs()

            Text(
                text = error.message ?: stringResource(id = R.string.error_need_retry),
                modifier = Modifier.constrainAs(message) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top, margin = 8.dp)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                },
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
            )

            Button(
                onClick = onRetry,
                modifier = Modifier.constrainAs(retryButton) {
                    start.linkTo(parent.start)
                    top.linkTo(message.bottom, margin = 8.dp)
                    end.linkTo(parent.end)
                },
            ) {
                Icon(
                    imageVector = Icons.Filled.Refresh,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp),
                )
                Text(text = stringResource(id = R.string.button_retry))
            }
        }
    }
}
