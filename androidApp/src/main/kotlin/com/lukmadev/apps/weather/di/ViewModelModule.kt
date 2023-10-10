package com.lukmadev.apps.weather.di

import com.lukmadev.apps.weather.feature.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::HomeViewModel)
}
