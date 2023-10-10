package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.City
import kotlinx.coroutines.flow.Flow

internal interface GeocodingDataSource {
    suspend fun findCities(query: String?): Flow<List<City>>
}
