package com.lukmadev.core.di

import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.data.geocoding.network.NetworkGeocodingDataSource
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val dataSourceModule = module {
    // Geocoding
    factory<GeocodingDataSource>(named(DataSourceQualifier.Network)) {
        NetworkGeocodingDataSource(get(named(HttpClientQualifier.Main.Simple)))
    }
}
