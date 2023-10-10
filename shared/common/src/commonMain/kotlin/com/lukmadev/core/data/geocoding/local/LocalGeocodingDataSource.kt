package com.lukmadev.core.data.geocoding.local

import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.domain.geocoding.City

internal class LocalGeocodingDataSource(
    private val database: AppDatabase,
) : GeocodingDataSource {

    override suspend fun markCityAsFavorite(city: City) {
        TODO("Not yet implemented")
    }
}
