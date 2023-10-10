package com.lukmadev.apps.weather.feature.home

import com.lukmadev.core.domain.geocoding.City

data class CityListItemModel(
    val city: City,
    val isFavorite: Boolean,
)
