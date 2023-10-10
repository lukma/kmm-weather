package com.lukmadev.core.data.geocoding.network

import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.domain.geocoding.City
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow

internal class NetworkGeocodingDataSource(
    private val httpClient: HttpClient
) : GeocodingDataSource {

    override suspend fun findCities(query: String?): Flow<List<City>> {
        TODO("Not yet implemented")
    }
}
