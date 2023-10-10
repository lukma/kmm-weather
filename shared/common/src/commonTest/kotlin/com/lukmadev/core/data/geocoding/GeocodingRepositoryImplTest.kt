package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.GeocodingRepository
import io.mockk.mockk

class GeocodingRepositoryImplTest {
    private val networkGeocodingDataSource: GeocodingDataSource = mockk()
    private val repository: GeocodingRepository = GeocodingRepositoryImpl(
        networkGeocodingDataSource,
    )
}
