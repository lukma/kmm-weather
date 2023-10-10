package com.lukmadev.core.data.forecast.network

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/data/2.5/")
internal class ForecastResource {

    @Serializable
    @Resource("forecast")
    class Forecast(
        val parent: ForecastResource = ForecastResource(),
        val cnt: Int = CNT,
        val lat: Double,
        val lon: Double,
    )

    companion object {
        private const val CNT = 3
    }
}
