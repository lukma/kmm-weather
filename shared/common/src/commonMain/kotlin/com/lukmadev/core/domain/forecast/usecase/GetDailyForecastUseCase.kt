package com.lukmadev.core.domain.forecast.usecase

import com.lukmadev.core.domain.common.usecase.FlowUseCase
import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.forecast.ForecastRepository
import kotlinx.coroutines.flow.Flow

class GetDailyForecastUseCase internal constructor(
    private val forecastRepository: ForecastRepository,
) : FlowUseCase<GetDailyForecastUseCase.Param, List<DailyForecast>>() {

    override suspend fun build(): Flow<List<DailyForecast>> {
        return forecastRepository.getDailyForecast(
            latitude = param.latitude,
            longitude = param.longitude,
        )
    }

    data class Param(
        val latitude: Double,
        val longitude: Double,
    )
}
