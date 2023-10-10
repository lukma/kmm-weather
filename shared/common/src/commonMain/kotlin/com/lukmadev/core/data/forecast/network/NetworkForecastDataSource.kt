package com.lukmadev.core.data.forecast.network

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.domain.forecast.DailyForecast
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

internal class NetworkForecastDataSource(
    private val httpClient: HttpClient
) : ForecastDataSource {

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): Flow<List<DailyForecast>> {
        TODO("Not yet implemented")
    }
}
