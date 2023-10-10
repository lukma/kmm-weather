package com.lukmadev.apps.weather.di

import com.lukmadev.apps.weather.feature.home.HomeViewModel
import com.lukmadev.apps.weather.util.CoroutinesTestRule
import com.lukmadev.apps.weather.util.KoinTestRule
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import kotlin.test.assertNotNull

class ViewModelModuleTest : KoinTest {

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        module = module {
            single<FindCitiesUseCase> { mockk() }
            single<GetFavoriteCitiesUseCase> { mockk() }
            single<ToggleFavoriteCityUseCase> { mockk() }
            includes(viewModelModule)
        }
    )

    @Test
    fun `should inject HomeViewModel`() {
        // when
        val component = get<HomeViewModel>()

        // then
        assertNotNull(component)
    }
}
