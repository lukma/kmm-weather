package com.lukmadev.core.data.forecast

import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.forecast.ForecastRepository
import kotlinx.coroutines.flow.Flow

internal class ForecastRepositoryImpl(
    private val networkForecastDataSource: ForecastDataSource,
) : ForecastRepository {

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): Flow<List<DailyForecast>> {
        return networkForecastDataSource.getDailyForecast(
            latitude = latitude,
            longitude = longitude,
        )
    }
}
