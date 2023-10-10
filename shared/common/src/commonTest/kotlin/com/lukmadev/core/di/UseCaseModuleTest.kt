package com.lukmadev.core.di

import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import com.lukmadev.core.util.ModuleTest
import org.koin.test.get
import kotlin.test.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNotNull

class UseCaseModuleTest : ModuleTest {

    @Test
    fun should_inject_FindCitiesUseCase() {
        // when
        val component1 = get<FindCitiesUseCase>()
        val component2 = get<FindCitiesUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }

    @Test
    fun should_inject_ToggleFavoriteCityUseCase() {
        // when
        val component1 = get<ToggleFavoriteCityUseCase>()
        val component2 = get<ToggleFavoriteCityUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }

    @Test
    fun should_inject_GetFavoriteCitiesUseCase() {
        // when
        val component1 = get<GetFavoriteCitiesUseCase>()
        val component2 = get<GetFavoriteCitiesUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }

    @Test
    fun should_inject_GetDailyForecastUseCase() {
        // when
        val component1 = get<GetDailyForecastUseCase>()
        val component2 = get<GetDailyForecastUseCase>()

        // then
        assertNotNull(component1)
        assertNotNull(component2)
        assertNotEquals(component1, component2)
    }
}
