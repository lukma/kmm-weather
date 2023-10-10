package com.lukmadev.core.util

import app.cash.sqldelight.db.SqlDriver
import com.lukmadev.core.data.db.AppDatabase
import com.lukmadev.core.di.commonModule
import com.lukmadev.core.di.databaseModule
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface DatabaseTest : KoinTest {

    @BeforeTest
    fun setup() {
        runTest {
            val sqlDriver = createTestSqlDriver()
            val testPlatformModule = module {
                single { sqlDriver }
                single {
                    AppDatabase.Schema.create(get())
                    AppDatabase(
                        get(),
                    )
                }
            }
            startKoin {
                modules(listOf(commonModule(mockk()), databaseModule, testPlatformModule))
            }
        }
    }

    @AfterTest
    fun tearDown() {
        get<SqlDriver>().close()
        stopKoin()
    }
}
