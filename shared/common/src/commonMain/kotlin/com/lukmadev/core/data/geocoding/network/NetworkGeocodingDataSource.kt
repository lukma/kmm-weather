package com.lukmadev.core.data.geocoding.network

import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.data.geocoding.network.dto.CityDTO
import com.lukmadev.core.data.geocoding.network.dto.toCity
import com.lukmadev.core.domain.geocoding.City
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NetworkGeocodingDataSource(
    private val httpClient: HttpClient
) : GeocodingDataSource {

    override suspend fun findCities(query: String?): Flow<List<City>> {
        return flow {
            val resource = GeocodingResource.Direct(q = query)
            val response = httpClient.get(resource)
                .body<List<CityDTO>>()
                .map { it.toCity() }
            emit(response)
        }
    }
}
