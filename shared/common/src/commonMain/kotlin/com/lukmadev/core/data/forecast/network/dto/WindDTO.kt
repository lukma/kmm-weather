package com.lukmadev.core.data.forecast.network.dto

import com.lukmadev.core.domain.forecast.Wind
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WindDTO(
    @SerialName("speed")
    val speed: Double,
    @SerialName("deg")
    val degrees: Double,
    @SerialName("gust")
    val gust: Double,
)

internal fun WindDTO.toWind() = Wind(
    speed = speed,
    degrees = degrees,
    gust = gust,
)
