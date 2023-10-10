package com.lukmadev.core.data.forecast

import com.lukmadev.core.domain.forecast.ForecastRepository
import com.lukmadev.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ForecastRepositoryImplTest {
    private val networkForecastDataSource: ForecastDataSource = mockk()
    private val repository: ForecastRepository = ForecastRepositoryImpl(
        networkForecastDataSource = networkForecastDataSource,
    )

    @Test
    fun perform_getDailyForecast_got_value() = runTest {
        // given
        coEvery {
            networkForecastDataSource.getDailyForecast(any(), any())
        } returns flowOf(TestSamples.dailyForecast)

        // when
        val actual = repository.getDailyForecast(
            latitude = TestSamples.cities.first().latitude,
            longitude = TestSamples.cities.first().longitude,
        ).single()

        // then
        val expected = TestSamples.dailyForecast
        assertEquals(expected, actual)
        coVerify(exactly = 1) {
            networkForecastDataSource.getDailyForecast(
                latitude = TestSamples.cities.first().latitude,
                longitude = TestSamples.cities.first().longitude,
            )
        }
    }

    @Test
    fun perform_getDailyForecast_got_failure() = runTest {
        // given
        coEvery {
            networkForecastDataSource.getDailyForecast(any(), any())
        } returns flow { error("fail") }

        // when
        val actual = runCatching {
            repository.getDailyForecast(
                latitude = TestSamples.cities.first().latitude,
                longitude = TestSamples.cities.first().longitude,
            ).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
