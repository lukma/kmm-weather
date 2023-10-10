package com.lukmadev.core.domain.geocoding

import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun findCities(query: String?): Flow<List<City>>
}
