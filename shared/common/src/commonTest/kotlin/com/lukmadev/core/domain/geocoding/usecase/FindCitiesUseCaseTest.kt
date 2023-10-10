package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.geocoding.GeocodingRepository
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

class FindCitiesUseCaseTest {
    private val geocodingRepository: GeocodingRepository = mockk()
    private val useCase = FindCitiesUseCase(geocodingRepository)
    private val useCaseParam = FindCitiesUseCase.Param(
        query = TestSamples.cities.first().name,
    )

    @Test
    fun perform_invoke_got_value() = runTest {
        // given
        coEvery { geocodingRepository.findCities(any()) } returns flowOf(TestSamples.cities)

        // when
        val actual = useCase(useCaseParam).single()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
        coVerify {
            geocodingRepository.findCities(
                query = useCaseParam.query,
            )
        }
    }

    @Test
    fun perform_invoke_got_failure() = runTest {
        // given
        coEvery { geocodingRepository.findCities(any()) } returns flow { error("fail") }

        // when
        val actual = runCatching {
            useCase(useCaseParam).single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
