package com.lukmadev.core.data.forecast.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class OneCallDTO(
    @SerialName("daily")
    val daily: List<DailyForecastDTO>,
)
