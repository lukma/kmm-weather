package com.lukmadev.core.di

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.util.ModuleTest
import org.koin.core.qualifier.named
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class DataSourceModuleTest : ModuleTest {

    @Test
    fun should_inject_Network_GeocodingDataSource() {
        // when
        val component1 = get<GeocodingDataSource>(named(DataSourceQualifier.Network))
        val component2 = get<GeocodingDataSource>(named(DataSourceQualifier.Network))

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }

    @Test
    fun should_inject_Network_ForecastDataSource() {
        // when
        val component1 = get<ForecastDataSource>(named(DataSourceQualifier.Network))
        val component2 = get<ForecastDataSource>(named(DataSourceQualifier.Network))

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }
}
