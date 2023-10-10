package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.GeocodingRepository

internal class GeocodingRepositoryImpl(
    private val networkGeocodingDataSource: GeocodingDataSource,
) : GeocodingRepository {
}
