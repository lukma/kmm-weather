package com.lukmadev.core.domain.forecast.usecase

import com.lukmadev.core.domain.forecast.ForecastRepository
import com.lukmadev.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertFails

class GetDailyForecastUseCaseTest {
    private val forecastRepository: ForecastRepository = mockk()
    private val useCase = GetDailyForecastUseCase(forecastRepository)
    private val useCaseParam = GetDailyForecastUseCase.Param(
        latitude = TestSamples.cities.first().latitude,
        longitude = TestSamples.cities.first().longitude,
    )

    @Test
    fun perform_invoke_got_value() = runTest {
        // given
        coEvery {
            forecastRepository.getDailyForecast(any(), any())
        } returns flowOf(TestSamples.dailyForecast)

        // when
        val actual = useCase(useCaseParam).single()

        // then
        val expected = TestSamples.dailyForecast
        assertEquals(expected, actual)
        coVerify {
            forecastRepository.getDailyForecast(
                latitude = useCaseParam.latitude,
                longitude = useCaseParam.longitude,
            )
        }
    }

    @Test
    fun perform_invoke_got_failure() = runTest {
        // given
        coEvery {
            forecastRepository.getDailyForecast(any(), any())
        } returns flow { error("fail") }

        // when
        val actual = runCatching {
            useCase(useCaseParam).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
