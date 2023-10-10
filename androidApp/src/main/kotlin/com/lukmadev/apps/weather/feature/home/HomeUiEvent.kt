package com.lukmadev.apps.weather.feature.home

import com.lukmadev.core.domain.geocoding.City

sealed class HomeUiEvent {
    data object ShowFavoriteCities : HomeUiEvent()
    data class TypeQuery(val query: String) : HomeUiEvent()
    data class ToggleFavorite(val city: City) : HomeUiEvent()
}
