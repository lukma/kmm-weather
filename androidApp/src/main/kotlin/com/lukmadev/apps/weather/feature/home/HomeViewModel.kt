package com.lukmadev.apps.weather.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmadev.core.domain.common.entity.onSuccess
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val findCitiesUseCase: FindCitiesUseCase,
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase,
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> get() = _uiState

    private val uiEvent = MutableSharedFlow<HomeUiEvent>()

    private var queryJob: Job? = null

    init {
        viewModelScope.launch { uiEvent.collect(::handleEvent) }
    }

    fun sendEvent(event: HomeUiEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private suspend fun handleEvent(event: HomeUiEvent) {
        when (event) {
            is HomeUiEvent.ShowFavoriteCities -> showFavoriteCities()
            is HomeUiEvent.ReloadFindCities -> {
                _uiState.update { it.copy(error = null) }
                findCities()
            }
            is HomeUiEvent.TypeQuery -> onQueryChanged(query = event.query)
            is HomeUiEvent.ToggleFavorite -> toggleFavorite(city = event.city)
        }
    }

    private suspend fun showFavoriteCities() {
        getFavoriteCitiesUseCase()
            .map { cities ->
                cities.map { city ->
                    CityListItemModel.Loaded(city = city, isFavorite = true)
                }
            }
            .collectLatest { cities ->
                _uiState.update { it.copy(listOfCities = cities) }
            }
    }

    private suspend fun onQueryChanged(query: String) {
        if (uiState.value.query != query && query.isNotBlank()) {
            _uiState.update { it.copy(query = query) }
            queryJob?.cancel()
            queryJob = viewModelScope.launch {
                delay(DEBOUNCE_FETCH_NETWORK)
                findCities()
            }
        } else if (uiState.value.query != query && query.isBlank()) {
            _uiState.update { it.copy(query = query) }
            showFavoriteCities()
        }
    }

    private suspend fun findCities() {
        _uiState.update { it.copy(listOfCities = listOf(CityListItemModel.Loading)) }

        val param = FindCitiesUseCase.Param(uiState.value.query)
        combine(
            getFavoriteCitiesUseCase(),
            findCitiesUseCase(param),
        ) { favoritesResult, findResult ->
            findResult.map { city ->
                CityListItemModel.Loaded(
                    city = city,
                    isFavorite = favoritesResult.any {
                        city.latitude == it.latitude && city.longitude == it.longitude
                    },
                )
            }
        }
            .catch { cause ->
                _uiState.update { it.copy(error = cause, listOfCities = emptyList()) }
            }
            .collectLatest { cities ->
                _uiState.update { it.copy(listOfCities = cities) }
            }
    }

    private suspend fun toggleFavorite(city: City) {
        val param = ToggleFavoriteCityUseCase.Param(city)
        toggleFavoriteCityUseCase(param)
            .onSuccess {
                _uiState.update {
                    val index = it.listOfCities.mapNotNull { item ->
                        (item as? CityListItemModel.Loaded)?.city
                    }.indexOf(city)
                    if (it.isShowSearchResult) {
                        val selectedItem =
                            it.listOfCities.getOrNull(index) as? CityListItemModel.Loaded
                                ?: return@onSuccess
                        val updatedListOfCities = it.listOfCities.toMutableList().apply {
                            set(index, selectedItem.copy(isFavorite = !selectedItem.isFavorite))
                        }
                        it.copy(listOfCities = updatedListOfCities)
                    } else {
                        it.copy(listOfCities = it.listOfCities.toMutableList().apply {
                            removeAt(index)
                        })
                    }
                }
            }
    }

    companion object {
        private const val DEBOUNCE_FETCH_NETWORK = 1500L
    }
}
