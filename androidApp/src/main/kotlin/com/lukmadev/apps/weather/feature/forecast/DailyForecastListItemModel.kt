package com.lukmadev.apps.weather.feature.forecast

import com.lukmadev.core.domain.forecast.DailyForecast

sealed class DailyForecastListItemModel {
    data object Loading : DailyForecastListItemModel()
    data class Loaded(
        val dailyForecast: DailyForecast,
    ) : DailyForecastListItemModel()
}
