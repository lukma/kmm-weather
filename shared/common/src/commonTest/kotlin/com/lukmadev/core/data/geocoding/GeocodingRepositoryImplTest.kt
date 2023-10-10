package com.lukmadev.core.data.geocoding

import com.lukmadev.core.domain.geocoding.GeocodingRepository
import com.lukmadev.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GeocodingRepositoryImplTest {
    private val localGeocodingDataSource: GeocodingDataSource = mockk()
    private val networkGeocodingDataSource: GeocodingDataSource = mockk()
    private val repository: GeocodingRepository = GeocodingRepositoryImpl(
        localGeocodingDataSource = localGeocodingDataSource,
        networkGeocodingDataSource = networkGeocodingDataSource,
    )

    @Test
    fun perform_findCities_got_value() = runTest {
        // given
        coEvery { networkGeocodingDataSource.findCities(any()) } returns flowOf(TestSamples.cities)

        // when
        val actual = repository.findCities(
            query = TestSamples.cities.first().name,
        ).single()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            networkGeocodingDataSource.findCities(query = TestSamples.cities.first().name)
        }
    }

    @Test
    fun perform_findCities_got_failure() = runTest {
        // given
        coEvery { networkGeocodingDataSource.findCities(any()) } returns flow { error("fail") }

        // when
        val actual = runCatching {
            repository.findCities(
                query = TestSamples.cities.first().name,
            ).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }

    @Test
    fun perform_markCityAsFavorite_got_success() = runTest {
        // given
        coJustRun { localGeocodingDataSource.markCityAsFavorite(any()) }

        // when
        repository.markCityAsFavorite(
            city = TestSamples.cities.first(),
        )

        // then
        coVerify(exactly = 1) {
            localGeocodingDataSource.markCityAsFavorite(city = TestSamples.cities.first())
        }
    }

    @Test
    fun perform_markCityAsFavorite_got_failure() = runTest {
        // given
        coEvery { networkGeocodingDataSource.markCityAsFavorite(any()) } answers { error("fail") }

        // when
        val actual = runCatching {
            repository.markCityAsFavorite(
                city = TestSamples.cities.first(),
            )
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
