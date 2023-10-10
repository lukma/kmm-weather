package com.lukmadev.core.domain.geocoding.usecase

import com.lukmadev.core.domain.common.entity.Result
import com.lukmadev.core.domain.geocoding.GeocodingRepository
import com.lukmadev.core.util.TestSamples
import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlin.test.Test
import kotlinx.coroutines.test.runTest
import kotlin.test.assertEquals
import kotlin.test.assertFails

class MarkCityAsFavoriteUseCaseTest {
    private val geocodingRepository: GeocodingRepository = mockk()
    private val useCase = MarkCityAsFavoriteUseCase(geocodingRepository)
    private val useCaseParam = MarkCityAsFavoriteUseCase.Param(
        city = TestSamples.cities.first(),
    )

    @Test
    fun perform_invoke_got_success() = runTest {
        // given
        coJustRun { geocodingRepository.markCityAsFavorite(any()) }

        // when
        val actual = useCase(useCaseParam)

        // then
        val expected = Result.Success(Unit)
        assertEquals(expected, actual)
        coVerify {
            geocodingRepository.markCityAsFavorite(
                city = useCaseParam.city,
            )
        }
    }

    @Test
    fun perform_invoke_got_failure() = runTest {
        // given
        coEvery { geocodingRepository.markCityAsFavorite(any()) } answers { error("fail") }

        // when
        val actual = useCase(useCaseParam)
            .let { (it as Result.Failure).error }

        // then
        assertFails { throw actual }
    }
}
