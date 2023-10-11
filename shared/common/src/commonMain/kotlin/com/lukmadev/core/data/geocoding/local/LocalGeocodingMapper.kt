package com.lukmadev.core.data.geocoding.local

import com.lukmadev.core.data.db.FavoriteCityTable
import com.lukmadev.core.domain.geocoding.City

internal fun FavoriteCityTable.toCity() = City(
    name = name,
    latitude = latitude,
    longitude = longitude,
    state = state,
    country = country,
)

internal fun City.toFavoriteCityTable() = FavoriteCityTable(
    id = id,
    name = name,
    latitude = latitude,
    longitude = longitude,
    state = state,
    country = country,
)
