package com.lukmadev.core.di

import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

internal val useCaseModule = module {
    includes(repositoryModule)

    // Geocoding
    factoryOf(::FindCitiesUseCase)
}
