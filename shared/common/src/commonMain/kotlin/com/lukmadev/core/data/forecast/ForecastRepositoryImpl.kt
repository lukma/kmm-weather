package com.lukmadev.core.data.forecast

import com.lukmadev.core.domain.forecast.ForecastRepository

internal class ForecastRepositoryImpl(
    private val networkForecastDataSource: ForecastDataSource,
) : ForecastRepository {
}
