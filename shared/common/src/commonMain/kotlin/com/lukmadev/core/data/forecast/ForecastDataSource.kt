package com.lukmadev.core.data.forecast

import com.lukmadev.core.domain.forecast.DailyForecast
import kotlinx.coroutines.flow.Flow

internal interface ForecastDataSource {
    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
    ): Flow<List<DailyForecast>>
}
