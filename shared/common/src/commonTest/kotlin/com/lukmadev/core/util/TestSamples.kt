package com.lukmadev.core.util

import com.lukmadev.core.domain.forecast.DailyForecast
import com.lukmadev.core.domain.forecast.Wind
import com.lukmadev.core.domain.geocoding.City
import kotlinx.datetime.Instant

object TestSamples {
    val cities = listOf(
        City(
            name = "Semarang",
            latitude = -6.9903988,
            longitude = 110.4229104,
            state = "Central Java",
            country = "ID",
        ),
    )

    val dailyForecast = listOf(
        DailyForecast(
            date = Instant.parse("2023-10-09T15:00:00.000Z"),
            temperature = 302.11,
            humidity = 79.0,
            wind = Wind(
                speed = 1.21,
                degrees = 227.0,
                gust = 1.41,
            )
        ),
    )
}
