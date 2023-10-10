package com.lukmadev.apps.weather.feature.home

import com.lukmadev.apps.weather.util.CoroutinesTestRule
import com.lukmadev.apps.weather.util.TestSamples
import com.lukmadev.core.domain.common.entity.Result
import com.lukmadev.core.domain.geocoding.usecase.FindCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.GetFavoriteCitiesUseCase
import com.lukmadev.core.domain.geocoding.usecase.ToggleFavoriteCityUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
            query = "",
            listOfCities = TestSamples.favoriteCities.map {
                CityListItemModel(
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
    fun `send event TypeQuery with any query`() {
        // given
        coEvery { findCitiesUseCase(any()) } returns flowOf(TestSamples.allCities)
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = TestSamples.allCities.first().name,
            listOfCities = TestSamples.allCities.mapIndexed { index, city ->
                CityListItemModel(
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
                CityListItemModel(
                    city = it,
                    isFavorite = true,
                )
            },
        )
        assertEquals(expected, actual)
    }

    @Test
    fun `send event ToggleFavorite when initial toggle`() {
        // given
        coEvery { findCitiesUseCase(any()) } returns flowOf(TestSamples.allCities)
        coEvery { getFavoriteCitiesUseCase() } returns flowOf(TestSamples.favoriteCities)
        coEvery { toggleFavoriteCityUseCase(any()) } returns Result.Success(Unit)

        // when
        viewModel.sendEvent(HomeUiEvent.TypeQuery(query = TestSamples.allCities.first().name))
        viewModel.sendEvent(HomeUiEvent.ToggleFavorite(city = TestSamples.allCities.last()))
        val actual = viewModel.uiState.value

        // then
        val expected = HomeUiState(
            query = TestSamples.allCities.first().name,
            listOfCities = TestSamples.allCities.map {
                CityListItemModel(
                    city = it,
                    isFavorite = true,
                )
            },
        )
        assertEquals(expected.listOfCities.map { it.isFavorite }, actual.listOfCities.map { it.isFavorite })
        coVerify(exactly = 1) {
            toggleFavoriteCityUseCase(any())
        }
    }
}
