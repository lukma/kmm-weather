package com.lukmadev.core.data.forecast

import com.lukmadev.core.domain.forecast.ForecastRepository
import io.mockk.mockk

class ForecastRepositoryImplTest {
    private val networkForecastDataSource: ForecastDataSource = mockk()
    private val repository: ForecastRepository = ForecastRepositoryImpl(
        networkForecastDataSource,
    )
}
