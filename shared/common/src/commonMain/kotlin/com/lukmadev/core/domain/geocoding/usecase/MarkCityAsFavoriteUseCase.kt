package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.common.usecase.SimpleUseCase
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.GeocodingRepository

class MarkCityAsFavoriteUseCase internal constructor(
    private val geocodingRepository: GeocodingRepository,
) : SimpleUseCase<MarkCityAsFavoriteUseCase.Param, Unit>() {

    override suspend fun build() {
        return geocodingRepository.markCityAsFavorite(
            city = param.city,
        )
    }

    data class Param(
        val city: City,
    )
}
