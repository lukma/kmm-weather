package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import kotlinx.coroutines.flow.Flow

internal class GeocodingRepositoryImpl(
    private val networkGeocodingDataSource: GeocodingDataSource,
) : GeocodingRepository {

    override suspend fun findCities(query: String?): Flow<List<City>> {
        return networkGeocodingDataSource.findCities(query)
    }
}
