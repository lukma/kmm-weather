package com.lukmadev.core.di

import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.MarkCityAsFavoriteUseCase
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
    fun should_inject_MarkCityAsFavoriteUseCase() {
        // when
        val component1 = get<MarkCityAsFavoriteUseCase>()
        val component2 = get<MarkCityAsFavoriteUseCase>()

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
}
