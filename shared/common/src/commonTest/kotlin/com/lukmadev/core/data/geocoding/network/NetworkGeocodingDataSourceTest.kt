package com.lukmadev.core.data.geocoding.network

import com.lukmadev.core.data.geocoding.GeocodingDataSource
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

class NetworkGeocodingDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: GeocodingDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkGeocodingDataSource(get())
    }

    @Test
    fun perform_findCities_got_value() = runTest {
        // given
        apiClient.mockResponse(
            body = """
                [
                    {
                        "name": "Semarang",
                        "lat": -6.9903988,
                        "lon": 110.4229104,
                        "country": "ID",
                        "state": "Central Java"
                    }
                ]
                """,
            status = HttpStatusCode.OK,
        )

        // when
        val actual = dataSource.findCities(
            query = TestSamples.cities.first().name,
        ).single()

        // then
        val expected = TestSamples.cities
        assertEquals(expected, actual)
    }

    @Test
    fun perform_findCities_got_failure() = runTest {
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
            dataSource.findCities(
                query = TestSamples.cities.first().name,
            ).single()
        }

        // then
        assertFailsWith<ClientRequestError> { actual.getOrThrow() }
    }
}
