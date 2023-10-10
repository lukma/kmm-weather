package com.lukmadev.core.di

import app.cash.sqldelight.db.SqlDriver
import com.lukmadev.core.util.ModuleTest
import io.ktor.client.engine.*
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class PlatformModuleTest : ModuleTest {

    @Test
    fun should_inject_SqlDriver() {
        // when
        val component1 = get<SqlDriver>()
        val component2 = get<SqlDriver>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }

    @Test
    fun should_inject_HttpClientEngine() {
        // when
        val component1 = get<HttpClientEngine>()
        val component2 = get<HttpClientEngine>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
