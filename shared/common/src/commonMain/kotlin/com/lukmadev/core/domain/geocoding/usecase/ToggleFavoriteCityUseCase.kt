package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.common.usecase.SimpleUseCase
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.GeocodingRepository

class ToggleFavoriteCityUseCase internal constructor(
    private val geocodingRepository: GeocodingRepository,
) : SimpleUseCase<ToggleFavoriteCityUseCase.Param, Unit>() {

    override suspend fun build() {
        return geocodingRepository.toggleFavoriteCity(
            city = param.city,
        )
    }

    data class Param(
        val city: City,
    )
}
