package com.lukmadev.apps.weather.feature.home

data class HomeUiState(
    val query: String = "",
    val listOfCities: List<CityListItemModel> = emptyList(),
) {

    val isShowSearchResult: Boolean get() = query.isNotBlank()
}
