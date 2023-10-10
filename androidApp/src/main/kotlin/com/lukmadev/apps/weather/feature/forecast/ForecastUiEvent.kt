package com.lukmadev.apps.weather.feature.forecast

import com.lukmadev.core.domain.geocoding.City

sealed class ForecastUiEvent {
    data object FetchDailyForecast : ForecastUiEvent()
    data class ToggleFavorite(val city: City) : ForecastUiEvent()
}
