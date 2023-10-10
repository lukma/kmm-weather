package com.lukmadev.core.data.geocoding.local

import app.cash.sqldelight.async.coroutines.awaitAsList
import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.domain.common.exception.DatabaseError
import com.lukmadev.core.domain.geocoding.City
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

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

    override suspend fun getFavoriteCities(): Flow<List<City>> {
        return flow {
            val result = database.favoriteCityQueries
                .finds(::mapCity)
                .awaitAsList()
            emit(result)
        }.catch { throw DatabaseError(it) }
    }
}
