package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

internal interface GeocodingDataSource {
    suspend fun findCities(query: String?): Flow<List<City>> = emptyFlow()
    suspend fun toggleFavoriteCity(city: City) = Unit
    suspend fun getFavoriteCities(): Flow<List<City>> = emptyFlow()
}
