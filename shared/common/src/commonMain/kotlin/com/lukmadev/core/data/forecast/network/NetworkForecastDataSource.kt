package com.lukmadev.core.data.forecast.network

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.data.forecast.network.dto.OneCallDTO
import com.lukmadev.core.data.forecast.network.dto.toDailyForecast
import com.lukmadev.core.domain.forecast.DailyForecast
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetworkForecastDataSource(
    private val httpClient: HttpClient
) : ForecastDataSource {

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double
    ): Flow<List<DailyForecast>> {
        return flow {
            val resource = ForecastResource.Daily(
                lat = latitude,
                lon = longitude,
            )
            val response = httpClient.get(resource)
                .body<OneCallDTO>()
                .daily
                .map { it.toDailyForecast() }
            emit(response)
        }
    }
}
