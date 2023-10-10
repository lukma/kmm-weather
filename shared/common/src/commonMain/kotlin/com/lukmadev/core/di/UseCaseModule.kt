package com.lukmadev.core.di

import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    includes(repositoryModule)

    // Geocoding
    factoryOf(::FindCitiesUseCase)
    factoryOf(::ToggleFavoriteCityUseCase)
    factoryOf(::GetFavoriteCitiesUseCase)

    // Forecast
    factoryOf(::GetDailyForecastUseCase)
}
