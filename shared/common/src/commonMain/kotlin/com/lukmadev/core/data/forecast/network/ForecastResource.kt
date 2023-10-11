package com.lukmadev.core.data.forecast.network

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/data/2.5/")
internal class ForecastResource {

    @Serializable
    @Resource("onecall")
    class Daily(
        val parent: ForecastResource = ForecastResource(),
        val units: String = UNITS,
        val exclude: String = EXCLUDE,
        val lat: Double,
        val lon: Double,
    )

    companion object {
        private const val UNITS = "metric"
        private const val EXCLUDE = "current,minutely,hourly,alerts"
    }
}
