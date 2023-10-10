package com.lukmadev.core.data.geocoding.local

import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.domain.common.exception.DatabaseError
import com.lukmadev.core.domain.geocoding.City

internal class LocalGeocodingDataSource(
    private val database: AppDatabase,
) : GeocodingDataSource {

    override suspend fun markCityAsFavorite(city: City) {
        try {
            database.transaction {
                with(database.favoriteCityQueries) {
                    upsert(favoriteCityTable = city.toFavoriteCityTable())
                }
            }
        } catch (cause: Exception) {
            throw DatabaseError(cause)
        }
    }
}
