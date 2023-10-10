package com.lukmadev.apps.weather.di

import com.lukmadev.apps.weather.feature.forecast.CityArg
import com.lukmadev.apps.weather.feature.forecast.ForecastViewModel
import com.lukmadev.apps.weather.feature.forecast.toCity
import com.lukmadev.apps.weather.feature.home.HomeViewModel
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModel { param ->
        val json = get<Json>()
        val city = json.decodeFromString<CityArg>(param.get()).toCity()
        ForecastViewModel(city, get())
    }
}
