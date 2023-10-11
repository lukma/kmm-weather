package com.lukmadev.apps.weather.feature.forecast

import com.lukmadev.apps.weather.util.CoroutinesTestRule
import com.lukmadev.apps.weather.util.TestSamples
import com.lukmadev.core.domain.common.entity.Result
import com.lukmadev.core.domain.common.exception.SerializationError
import com.lukmadev.core.domain.forecast.usecase.GetDailyForecastUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class ForecastViewModelTest {
    private val getDailyForecastUseCase: GetDailyForecastUseCase = mockk()
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase = mockk()
    private lateinit var viewModel: ForecastViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = ForecastViewModel(
            TestSamples.allCities.first(),
            getDailyForecastUseCase,
            toggleFavoriteCityUseCase,
        )
    }

    @Test
    fun `send event FetchDailyForecast got success`() {
        // given
        coEvery { getDailyForecastUseCase(any()) } returns flowOf(TestSamples.dailyForecast)

        // when
        viewModel.sendEvent(ForecastUiEvent.FetchDailyForecast)
        val actual = viewModel.uiState.value

        // then
        val expected = ForecastUiState(
            city = TestSamples.allCities.first(),
            dailyForecast = TestSamples.dailyForecast,
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            getDailyForecastUseCase(any())
        }
    }

    @Test
    fun `send event FetchDailyForecast got failure`() {
        // given
        val error = SerializationError(Throwable("fail"))
        coEvery { getDailyForecastUseCase(any()) } returns flow { throw error }

        // when
        viewModel.sendEvent(ForecastUiEvent.FetchDailyForecast)
        val actual = viewModel.uiState.value

        // then
        val expected = ForecastUiState(
            city = TestSamples.allCities.first(),
            error = error,
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `send event ToggleFavorite`() {
        // given
        coEvery { toggleFavoriteCityUseCase(any()) } returns Result.Success(Unit)

        // when
        viewModel.sendEvent(ForecastUiEvent.ToggleFavorite)
        val actual = viewModel.uiState.value

        // then
        val expected = ForecastUiState(
            city = TestSamples.allCities.first(),
            isFavorite = true,
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            toggleFavoriteCityUseCase(any())
        }
    }
}
