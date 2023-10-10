package com.lukmadev.core.di

import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.util.ModuleTest
import kotlinx.serialization.json.Json
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class CommonModuleTest : ModuleTest {

    @Test
    fun should_inject_AppConfig() {
        // when
        val component1 = get<AppConfig>()
        val component2 = get<AppConfig>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }

    @Test
    fun should_inject_Json() {
        // when
        val component1 = get<Json>()
        val component2 = get<Json>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
