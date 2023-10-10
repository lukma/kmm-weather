package com.lukmadev.apps.weather.feature.forecast

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.City
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ForecastViewModel(
    private val city: City,
    private val getDailyForecastUseCase: GetDailyForecastUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ForecastUiState())
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
    }

    private suspend fun toggleFavorite() {
    }
}
