package com.lukmadev.core.data.geocoding.network

import com.lukmadev.core.data.geocoding.GeocodingDataSource
import com.lukmadev.core.util.HttpClientTest
import io.ktor.client.HttpClient
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest

class NetworkGeocodingDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: GeocodingDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkGeocodingDataSource(get())
    }
}
