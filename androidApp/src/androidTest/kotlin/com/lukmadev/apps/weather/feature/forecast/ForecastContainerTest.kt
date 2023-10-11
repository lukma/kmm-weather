package com.lukmadev.apps.weather.feature.forecast

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import com.lukmadev.apps.weather.di.viewModelModule
import com.lukmadev.apps.weather.util.KoinTestRule
import com.lukmadev.apps.weather.util.TestSamples
import com.lukmadev.apps.weather.util.setScreen
import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get

class ForecastContainerTest : KoinTest {
    private val getDailyForecastUseCase: GetDailyForecastUseCase = mockk()
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase = mockk()

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        module = module {
            includes(viewModelModule)
            single {
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            }
            factory { getDailyForecastUseCase }
            factory { toggleFavoriteCityUseCase }
        }
    )

    @Before
    fun setup() {
        coEvery { getDailyForecastUseCase(any()) } returns flowOf(TestSamples.dailyForecast)
        composeTestRule.setScreen(
            withDummyStartDestination = true,
            navigateTo = {
                "forecast/${
                    get<Json>().encodeToString(
                        TestSamples.allCities.first().toCityArg()
                    )
                }"
            },
            component = ForecastContainer,
        )
    }

    @Test
    fun display_content() {
        // then
        composeTestRule.onNode(hasText(TestSamples.allCities.first().name))
            .assertIsDisplayed()
        TestSamples.dailyForecast.forEach {
            composeTestRule.onNode(hasText(it.temperature.toString(), substring = true))
                .assertIsDisplayed()
        }
    }
}
