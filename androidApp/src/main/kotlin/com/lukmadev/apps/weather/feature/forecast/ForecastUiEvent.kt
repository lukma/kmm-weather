package com.lukmadev.apps.weather.feature.forecast

sealed class ForecastUiEvent {
    data object FetchDailyForecast : ForecastUiEvent()
    data object ToggleFavorite : ForecastUiEvent()
}
