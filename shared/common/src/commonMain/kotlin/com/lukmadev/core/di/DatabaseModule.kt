package com.lukmadev.core.di

import app.cash.sqldelight.adapter.primitive.FloatColumnAdapter
import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.data.db.FavoriteCityTable
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val databaseModule = module {
    factory(named(DatabaseQualifier.TableAdapter.FavoriteCity)) {
        FavoriteCityTable.Adapter(
            latitudeAdapter = FloatColumnAdapter,
            longitudeAdapter = FloatColumnAdapter,
        )
    }

    single {
        AppDatabase(
            get(),
            get(named(DatabaseQualifier.TableAdapter.FavoriteCity)),
        )
    }
}
