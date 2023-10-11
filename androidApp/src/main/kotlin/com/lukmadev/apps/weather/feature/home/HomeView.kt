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
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukmadev.apps.weather.R
import com.lukmadev.apps.weather.feature.forecast.toCityArg
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.uikit.containment.ErrorView
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

@Composable
fun HomeView(
    uiState: HomeUiState,
    onSendEvent: (HomeUiEvent) -> Unit,
    navigateTo: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier
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

        if (uiState.error == null) {
            LazyColumn {
                items(uiState.listOfCities) {
                    CityItemView(
                        item = it,
                        toggle = { selected -> onSendEvent(HomeUiEvent.ToggleFavorite(selected)) },
                        showDailyForecast = { city ->
                            navigateTo(
                                object : KoinComponent {
                                    fun generateLink(): String {
                                        val cityArg = get<Json>().encodeToString(city.toCityArg())
                                        return "forecast/$cityArg"
                                    }
                                }.generateLink()
                            )
                        },
                        modifier = Modifier.fillMaxWidth(),
                    )

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                    )
                }
            }
        } else {
            ConstraintLayout(
                Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            ) {
                val (error) = createRefs()

                ErrorView(
                    error = uiState.error,
                    onRetry = { onSendEvent(HomeUiEvent.ReloadFindCities) },
                    modifier = Modifier.constrainAs(error) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, margin = 48.dp)
                        end.linkTo(parent.end)
                        width = Dimension.fillToConstraints
                        height = Dimension.wrapContent
                    },
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
                        isFavorite = true,
                    )
                ),
            ),
            onSendEvent = { /* no-op */ },
            navigateTo = { /* no-op */ },
        )
    }
}
