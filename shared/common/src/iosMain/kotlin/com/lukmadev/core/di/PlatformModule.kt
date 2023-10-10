package com.lukmadev.core.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.lukmadev.core.config.Constants
import com.lukmadev.core.data.db.AppDatabase
import io.ktor.client.engine.darwin.*
import org.koin.dsl.module

internal actual val platformModule = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = AppDatabase.Schema.synchronous(),
            name = Constants.databaseName,
        )
    }
    single { Darwin.create() }
}
