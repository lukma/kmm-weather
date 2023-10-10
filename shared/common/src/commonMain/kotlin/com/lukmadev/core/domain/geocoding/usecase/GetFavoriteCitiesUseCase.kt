package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.common.usecase.FlowUseCase
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import kotlinx.coroutines.flow.Flow

class GetFavoriteCitiesUseCase internal constructor(
    private val geocodingRepository: GeocodingRepository,
) : FlowUseCase<Nothing, List<City>>() {

    override suspend fun build(): Flow<List<City>> {
        return geocodingRepository.getFavoriteCities()
    }
}
