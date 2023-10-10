package com.lukmadev.core.di

import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.lukmadev.core.config.Constants
import com.lukmadev.core.data.db.AppDatabase
import io.ktor.client.engine.okhttp.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

internal actual val platformModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = AppDatabase.Schema.synchronous(),
            context = androidContext(),
            name = Constants.databaseName,
        )
    }
    single { OkHttp.create() }
}
