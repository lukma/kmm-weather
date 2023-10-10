package com.lukmadev.core.data.forecast.network

import com.lukmadev.core.data.forecast.ForecastDataSource
import com.lukmadev.core.util.HttpClientTest
import io.ktor.client.HttpClient
import org.koin.test.get
import org.koin.test.inject
import kotlin.test.BeforeTest

class NetworkForecastDataSourceTest : HttpClientTest {
    private val apiClient: HttpClient by inject()
    private lateinit var dataSource: ForecastDataSource

    @BeforeTest
    override fun setup() {
        super.setup()
        dataSource = NetworkForecastDataSource(get())
    }
}
