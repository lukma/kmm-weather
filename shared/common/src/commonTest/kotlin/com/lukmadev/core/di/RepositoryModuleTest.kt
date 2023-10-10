package com.lukmadev.core.di

import com.lukmadev.core.domain.geocoding.GeocodingRepository
import com.lukmadev.core.util.ModuleTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class RepositoryModuleTest : ModuleTest {

    @Test
    fun should_inject_GeocodingRepository() {
        // when
        val component1 = get<GeocodingRepository>()
        val component2 = get<GeocodingRepository>()

        // then
        assertNotNull(component1)
        assertEquals(component1, component2)
    }
}
