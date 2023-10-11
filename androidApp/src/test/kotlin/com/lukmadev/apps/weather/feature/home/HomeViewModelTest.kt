package com.lukmadev.apps.weather.feature.home

import com.lukmadev.apps.weather.util.CoroutinesTestRule
import com.lukmadev.apps.weather.util.TestSamples
import com.lukmadev.core.domain.common.entity.Result
import com.lukmadev.core.domain.common.exception.SerializationError
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class HomeViewModelTest {
    private val findCitiesUseCase: FindCitiesUseCase = mockk()
    private val getFavoriteCitiesUseCase: GetFavoriteCitiesUseCase = mockk()
    private val toggleFavoriteCityUseCase: ToggleFavoriteCityUseCase = mockk()
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var coroutinesTestRule = CoroutinesTestRule()

    @Before
    fun setup() {
        viewModel = HomeViewModel(
            findCitiesUseCase,
            getFavoriteCitiesUseCase,
            toggleFavoriteCityUseCase,
        )
    }

    @Test
    fun `send event ShowFavoriteCities with any query`() {
        // given
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)

        // when
        viewModel.sendEvent(HomeUiEvent.ShowFavoriteCities)
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            listOfCities = TestSamples.favoriteCities.map {
                CityListItemModel.Loaded(
                    city = it,
                    isFavorite = true,
                )
            },
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            getFavoriteCitiesUseCase()
        }
    }

    @Test
    fun `send event TypeQuery with any query`() = runTest {
        // given
        coEvery { findCitiesUseCase(any()) } returns flowOf(TestSamples.allCities)
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        delay(TimeUnit.SECONDS.toMillis(2))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = TestSamples.allCities.first().name,
            listOfCities = TestSamples.allCities.mapIndexed { index, city ->
                CityListItemModel.Loaded(
                    city = city,
                    isFavorite = index == 0,
                )
            },
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            findCitiesUseCase(any())
            getFavoriteCitiesUseCase()
        }
    }

    @Test
    fun `send event TypeQuery with blank query`() {
        // given
        coEvery { findCitiesUseCase(any()) } returns flowOf(TestSamples.allCities)
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = " "))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = " ",
            listOfCities = TestSamples.favoriteCities.map {
                CityListItemModel.Loaded(
                    city = it,
                    isFavorite = true,
                )
            },
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `send event TypeQuery got failure`() = runTest {
        // given
        val error = SerializationError(Throwable("fail"))
        coEvery { findCitiesUseCase(any()) } returns flow { throw error }
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        delay(TimeUnit.SECONDS.toMillis(2))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = TestSamples.allCities.first().name,
            error = error,
        )
        assertEquals(expected.error, actual.error)
    }

    @Test
    fun `send event ToggleFavorite in favorite cities`() {
        // given
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)
        coEvery { toggleFavoriteCityUseCase(any()) } returns Result.Success(Unit)

        // when
        viewModel.sendEvent(HomeUiEvent.ShowFavoriteCities)
        viewModel.sendEvent(HomeUiEvent.ToggleFavorite(city = TestSamples.favoriteCities.first()))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            listOfCities = emptyList(),
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            toggleFavoriteCityUseCase(any())
        }
    }

    @Test
    fun `send event ToggleFavorite in search result`() = runTest {
        // given
        coEvery { findCitiesUseCase(any()) } returns flowOf(TestSamples.allCities)
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)
        coEvery { toggleFavoriteCityUseCase(any()) } returns Result.Success(Unit)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        delay(TimeUnit.SECONDS.toMillis(2))
        viewModel.sendEvent(HomeUiEvent.ToggleFavorite(city = TestSamples.allCities.last()))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = TestSamples.allCities.first().name,
            listOfCities = TestSamples.allCities.map {
                CityListItemModel.Loaded(
                    city = it,
                    isFavorite = true,
                )
            },
        )
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            toggleFavoriteCityUseCase(any())
        }
    }
}
