package com.lukmadev.core.di

import com.lukmadev.core.data.forecast.ForecastRepositoryImpl
import com.lukmadev.core.data.geocoding.GeocodingRepositoryImpl
import com.lukmadev.core.domain.forecast.ForecastRepository
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val repositoryModule = module {
    includes(dataSourceModule)

    single<GeocodingRepository> {
        GeocodingRepositoryImpl(
            localGeocodingDataSource = get(named(DataSourceQualifier.Local)),
            networkGeocodingDataSource = get(named(DataSourceQualifier.Network)),
        )
    }

    single<ForecastRepository> {
        ForecastRepositoryImpl(
            networkForecastDataSource = get(named(DataSourceQualifier.Network)),
        )
    }
}
