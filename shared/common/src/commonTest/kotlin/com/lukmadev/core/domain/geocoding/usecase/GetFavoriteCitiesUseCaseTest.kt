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

class GetFavoriteCitiesUseCaseTest {
    private val geocodingRepository: GeocodingRepository = mockk()
    private val useCase = GetFavoriteCitiesUseCase(geocodingRepository)

    @Test
    fun perform_invoke_got_value() = runTest {
        // given
        coEvery { geocodingRepository.getFavoriteCities() } returns flowOf(TestSamples.cities)

        // when
        val actual = useCase().single()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
        coVerify {
            geocodingRepository.getFavoriteCities()
        }
    }

    @Test
    fun perform_invoke_got_failure() = runTest {
        // given
        coEvery { geocodingRepository.getFavoriteCities() } returns flow { error("fail") }

        // when
        val actual = runCatching {
            useCase().single()
        }

        // then
        assertFails { actual.getOrThrow() }
    }
}
