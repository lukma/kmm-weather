package com.lukmadev.apps.weather.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lukmadev.apps.weather.R
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.core.domain.geocoding.City

@Composable
fun HomeView(
    uiState: HomeUiState,
    onSendEvent: (HomeUiEvent) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {

        OutlinedTextField(
            value = uiState.query,
            onValueChange = { onSendEvent(HomeUiEvent.TypeQuery(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    testTag = "queryInput"
                },
            label = { Text(stringResource(id = R.string.textfield_search)) },
            leadingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = null)
            },
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(
                id = if (uiState.isShowSearchResult) R.string.label_search_result else R.string.label_favorite_city
            ),
            color = Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(uiState.listOfCities) {
                CityItemView(
                    item = it,
                    toggle = { selected -> onSendEvent(HomeUiEvent.ToggleFavorite(selected)) },
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    WeatherTheme {
        HomeView(
            uiState = HomeUiState(
                listOfCities = listOf(
                    CityListItemModel.Loaded(
                        city = City(
                            name = "Semarang",
                            latitude = -6.9903988,
                            longitude = 110.4229104,
                            state = "Central Java",
                            country = "ID",
                        ),
                        isFavorite = true
                    )
                ),
            ),
            onSendEvent = { /* no-op */ },
        )
    }
}
