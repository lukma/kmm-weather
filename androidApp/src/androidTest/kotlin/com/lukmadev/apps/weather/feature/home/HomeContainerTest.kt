package com.lukmadev.apps.weather.feature.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTextInput
import com.lukmadev.apps.weather.di.viewModelModule
import com.lukmadev.apps.weather.util.KoinTestRule
import com.lukmadev.apps.weather.util.TestSamples
import com.lukmadev.apps.weather.util.setScreen
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module

class HomeContainerTest {
    private val findCitiesUseCase: FindCitiesUseCase = mockk()
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase = mockk()
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase = mockk()

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        module = module {
            includes(viewModelModule)
            factory { findCitiesUseCase }
            factory { getFavoriteCitiesUseCase }
            factory { toggleFavoriteCityUseCase }
        }
    )

    @Before
    fun setup() {
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)
        composeTestRule.setScreen(component = HomeContainer(navigateTo = {}))
    }

    @Test
    fun perform_type_query() {
        // when
        composeTestRule.onNode(hasTestTag("queryInput"))
            .performTextInput(TestSamples.allCities.first().name.substring(0..2))

        // then
        composeTestRule.onNode(hasTestTag("queryInput"))
            .assertTextContains(TestSamples.allCities.first().name.substring(0..2))
        composeTestRule.onNode(hasText(TestSamples.allCities.first().name))
            .assertIsDisplayed()
    }
}
