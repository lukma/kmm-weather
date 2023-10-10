package com.lukmadev.core.di

import org.koin.dsl.module

internal val repositoryModule = module {
    includes(dataSourceModule)
}
