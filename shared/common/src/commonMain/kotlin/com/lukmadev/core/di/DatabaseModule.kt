package com.lukmadev.core.di

import com.lukmadev.core.data.db.AppDatabase
import org.koin.dsl.module

internal val databaseModule = module {
    single {
        AppDatabase(
            get(),
        )
    }
}
