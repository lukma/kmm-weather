package com.lukmadev.core.di

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.data.forecast.network.NetworkForecastDataSource
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.data.geocoding.local.LocalGeocodingDataSource
import com.lukmadev.core.data.geocoding.network.NetworkGeocodingDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dataSourceModule = module {
    // Geocoding
    factory<GeocodingDataSource>(named(DataSourceQualifier.Local)) {
        LocalGeocodingDataSource(get())
    }
    factory<GeocodingDataSource>(named(DataSourceQualifier.Network)) {
        NetworkGeocodingDataSource(get(named(HttpClientQualifier.Main.Simple)))
    }

    // Forecast
    factory<ForecastDataSource>(named(DataSourceQualifier.Network)) {
        NetworkForecastDataSource(get(named(HttpClientQualifier.Main.Simple)))
    }
}
