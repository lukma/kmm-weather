package com.lukmadev.core.data.geocoding.network

import com.lukmadev.core.data.geocoding.GeocodingDataSource
import io.ktor.client.HttpClient

internal class NetworkGeocodingDataSource(
    private val httpClient: HttpClient
) : GeocodingDataSource {
}
