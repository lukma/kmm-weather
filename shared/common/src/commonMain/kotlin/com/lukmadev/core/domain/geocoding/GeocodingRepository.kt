package com.lukmadev.core.domain.geocoding

import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun findCities(query: String?): Flow<List<City>>
    suspend fun toggleFavoriteCity(city: City)
    suspend fun getFavoriteCities(): Flow<List<City>>
}
