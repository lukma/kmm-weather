package com.lukmadev.core.data.forecast.network

import com.lukmadev.core.data.forecast.ForecastDataSource
import io.ktor.client.HttpClient

internal class NetworkForecastDataSource(
    private val httpClient: HttpClient
) : ForecastDataSource {
}
