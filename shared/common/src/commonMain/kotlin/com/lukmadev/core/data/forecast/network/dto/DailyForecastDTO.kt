package com.lukmadev.core.data.forecast.network.dto

import com.lukmadev.core.domain.forecast.DailyForecast
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastDTO(
    @SerialName("dt")
    val date: Long,
    @SerialName("main")
    val main: Main,
    @SerialName("wind")
    val wind: WindDTO,
) {

    @Serializable
    data class Main(
        @SerialName("temp")
        val temperature: Double,
        @SerialName("humidity")
        val humidity: Double,
    )
}

internal fun DailyForecastDTO.toDailyForecast() = DailyForecast(
    date = Instant.fromEpochSeconds(date),
    temperature = main.temperature,
    humidity = main.humidity,
    wind = wind.toWind(),
)
