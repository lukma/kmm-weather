package com.lukmadev.core

import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.MarkCityAsFavoriteUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun initKoin(appConfig: AppConfig) {
    initKoin(appConfig) {}
}

object CommonDependencies : KoinComponent {
    // Geocoding
    val findCitiesUseCase: FindCitiesUseCase get() = get()
    val markCityAsFavoriteUseCase: MarkCityAsFavoriteUseCase get() = get()
    val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase get() = get()

    // Forecast
    val getDailyForecastUseCase: GetDailyForecastUseCase get() = get()
}
