package com.lukmadev.core.data.forecast.network.dto

import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.forecast.Wind
import kotlinx.datetime.Instant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DailyForecastDTO(
    @SerialName("dt")
    val date: Long,
    @SerialName("temp")
    val temperature: Temperature,
    @SerialName("humidity")
    val humidity: Double,
    @SerialName("wind_speed")
    val speed: Double,
    @SerialName("wind_deg")
    val degrees: Double,
    @SerialName("wind_gust")
    val gust: Double,
) {

    @Serializable
    data class Temperature(
        @SerialName("day")
        val day: Double,
    )
}

internal fun DailyForecastDTO.toDailyForecast() = DailyForecast(
    date = Instant.fromEpochSeconds(date),
    temperature = temperature.day,
    humidity = humidity,
    wind = Wind(
        speed = speed,
        degrees = degrees,
        gust = gust,
    ),
)
