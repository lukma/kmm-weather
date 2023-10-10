package com.lukmadev.core.domain.forecast

import kotlinx.coroutines.flow.Flow

interface ForecastRepository {
    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
    ): Flow<List<DailyForecast>>
}
