package com.lukmadev.core.util

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.lukmadev.core.data.db.AppDatabase

actual suspend fun createTestSqlDriver(): SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    .also { AppDatabase.Schema.awaitCreate(it) }
