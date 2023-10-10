package com.lukmadev.core.di

import com.lukmadev.core.util.ModuleTest
import io.ktor.client.*
import org.koin.core.qualifier.named
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NetworkModuleTest : ModuleTest {

    @Test
    fun should_inject_Simple_Main_HttpClient() {
        // when
        val component1 = get<HttpClient>(named(HttpClientQualifier.Main.Simple))
        val component2 = get<HttpClient>(named(HttpClientQualifier.Main.Simple))

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
