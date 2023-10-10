package com.lukmadev.core.util

import com.lukmadev.core.initKoin
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.koin.core.context.loadKoinModules
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface ModuleTest : KoinTest {

    @BeforeTest
    fun setup() = runTest {
        initKoin(appConfig = mockk(relaxed = true)) {}
        val sqlDriver = createTestSqlDriver()
        val testPlatformModule = module {
            single { sqlDriver }
        }
        loadKoinModules(testPlatformModule)
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }
}
