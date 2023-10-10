package com.lukmadev.core.data.forecast.network

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.domain.common.exception.ClientRequestError
import com.lukmadev.core.util.HttpClientTest
import com.lukmadev.core.util.TestSamples
import io.ktor.client.HttpClient
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class NetworkForecastDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: ForecastDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkForecastDataSource(get())
    }

    @Test
    fun perform_getDailyForecast_got_value() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "list": [
                        {
                            "dt": 1696863600,
                            "main": {
                                "temp": 302.11,
                                "humidity": 79
                            },
                            "wind": {
                                "speed": 1.21,
                                "deg": 227,
                                "gust": 1.41
                            },
                            "dt_txt": "2023-10-09 15:00:00"
                        }
                    ]
                }
                """,
            status = HttpStatusCode.OK,
        )

        // when
        val actual = dataSource.getDailyForecast(
            latitude = TestSamples.cities.first().latitude,
            longitude = TestSamples.cities.first().longitude,
        ).single()

        // then
        val expected = TestSamples.dailyForecast
        assertEquals(expected, actual)
    }

    @Test
    fun perform_getDailyForecast_got_failure() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                {
                    "cod": "400",
                    "message": "Nothing to geocode"
                }
                """,
            status = HttpStatusCode.BadRequest,
        )

        // when
        val actual = runCatching {
            dataSource.getDailyForecast(
                latitude = TestSamples.cities.first().latitude,
                longitude = TestSamples.cities.first().longitude,
            ).single()
        }

        // then
        assertFailsWith<ClientRequestError> { actual.getOrThrow() }
    }
}
