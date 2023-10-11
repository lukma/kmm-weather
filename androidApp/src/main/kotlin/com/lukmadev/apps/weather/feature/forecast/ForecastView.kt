package com.lukmadev.apps.weather.feature.forecast

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.lukmadev.apps.weather.feature.home.CityItemView
import com.lukmadev.apps.weather.feature.home.CityListItemModel
import com.lukmadev.apps.weather.ui.theme.WeatherTheme
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.uikit.containment.ErrorView

@Composable
fun ForecastView(
    uiState: ForecastUiState,
    onSendEvent: (ForecastUiEvent) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
    ) {
        CityItemView(
            item = CityListItemModel.Loaded(city = uiState.city, isFavorite = true),
            toggle = { onSendEvent(ForecastUiEvent.ToggleFavorite) },
            showDailyForecast = { /* no-op */ },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (uiState.error == null) {
            LazyColumn {
                items(uiState.dailyForecast) {
                    DailyForecastView(item = it)

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
                    onRetry = { onSendEvent(ForecastUiEvent.FetchDailyForecast) },
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
        ForecastView(
            uiState = ForecastUiState(
                city = City(
                    name = "Semarang",
                    latitude = -6.9903988,
                    longitude = 110.4229104,
                    state = "Central Java",
                    country = "ID",
                ),
                isFavorite = false,
            ),
            onSendEvent = { /* no-op */ },
        )
    }
}
