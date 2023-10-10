package com.lukmadev.core.util

import app.cash.sqldelight.db.SqlDriver

@Suppress("NO_ACTUAL_FOR_EXPECT")
expect suspend fun createTestSqlDriver(): SqlDriver
