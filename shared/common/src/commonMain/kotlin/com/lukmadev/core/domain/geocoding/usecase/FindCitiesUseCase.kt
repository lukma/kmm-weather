package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.common.usecase.FlowUseCase
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import kotlinx.coroutines.flow.Flow

class FindCitiesUseCase internal constructor(
    private val geocodingRepository: GeocodingRepository,
) : FlowUseCase<FindCitiesUseCase.Param, List<City>>() {

    override suspend fun build(): Flow<List<City>> {
        return geocodingRepository.findCities(
            query = param.query,
        )
    }

    data class Param(
        val query: String? = null,
    )
}
