package com.lukmadev.core.di

import com.lukmadev.core.data.geocoding.GeocodingRepositoryImpl
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val repositoryModule = module {
    includes(dataSourceModule)

    single<GeocodingRepository> {
        GeocodingRepositoryImpl(
            get(named(DataSourceQualifier.Network)),
        )
    }
}
