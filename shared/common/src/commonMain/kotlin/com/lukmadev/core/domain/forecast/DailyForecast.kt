package com.lukmadev.core.domain.forecast

import kotlinx.datetime.Instant

data class DailyForecast(
    val date: Instant,
    val temperature: Double,
    val humidity: Double,
    val wind: Wind,
)
