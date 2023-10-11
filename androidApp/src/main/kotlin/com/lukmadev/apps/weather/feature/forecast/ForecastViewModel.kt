package com.lukmadev.apps.weather.feature.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmadev.core.domain.common.entity.onSuccess
import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.City
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val city: City,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForecastUiState(city = city))
    val uiState: StateFlow<ForecastUiState> get() = _uiState

    private val uiEvent = MutableSharedFlow<ForecastUiEvent>()

    init {
        viewModelScope.launch { uiEvent.collect(::handleEvent) }
    }

    fun sendEvent(event: ForecastUiEvent) {
        viewModelScope.launch { uiEvent.emit(event) }
    }

    private suspend fun handleEvent(event: ForecastUiEvent) {
        when (event) {
            is ForecastUiEvent.FetchDailyForecast -> fetchDailyForecast()
            is ForecastUiEvent.ToggleFavorite -> toggleFavorite()
        }
    }

    private suspend fun fetchDailyForecast() {
        _uiState.update { it.copy(isLoading = true) }
        val param = GetDailyForecastUseCase.Param(
            latitude = city.latitude,
            longitude = city.longitude,
        )
        getDailyForecastUseCase(param)
            .catch { cause ->
                _uiState.update { it.copy(error = cause, isLoading = false) }
            }
            .collectLatest { forecast ->
                _uiState.update { it.copy(dailyForecast = forecast, isLoading = false) }
            }
    }

    private suspend fun toggleFavorite() {
        val param = ToggleFavoriteCityUseCase.Param(city)
        toggleFavoriteCityUseCase(param)
            .onSuccess {
                _uiState.update { it.copy(isFavorite = !it.isFavorite) }
            }
    }
}
