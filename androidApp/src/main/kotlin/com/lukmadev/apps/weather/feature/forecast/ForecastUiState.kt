package com.lukmadev.apps.weather.feature.forecast

import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.geocoding.City

data class ForecastUiState(
    val city: City,
    val isFavorite: Boolean = false,
    val dailyForecast: List<DailyForecast> = emptyList(),
    val isLoading: Boolean = false,
    val error: Throwable? = null,
)
