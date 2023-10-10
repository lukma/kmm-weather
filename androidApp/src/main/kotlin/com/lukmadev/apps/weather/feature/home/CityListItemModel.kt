package com.lukmadev.apps.weather.feature.home

import com.lukmadev.core.domain.geocoding.City

sealed class CityListItemModel {
    data object Loading : CityListItemModel()
    data class Loaded(
        val city: City,
        val isFavorite: Boolean,
    ) : CityListItemModel()
}
