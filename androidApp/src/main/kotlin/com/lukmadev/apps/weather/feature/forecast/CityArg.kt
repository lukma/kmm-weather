package com.lukmadev.apps.weather.feature.forecast

import com.lukmadev.core.domain.geocoding.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CityArg(
    @SerialName("name")
    val name: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("state")
    val state: String? = null,
    @SerialName("country")
    val country: String,
)

internal fun City.toCityArg() = CityArg(
    name = name,
    latitude = latitude,
    longitude = longitude,
    state = state,
    country = country,
)

internal fun CityArg.toCity() = City(
    name = name,
    latitude = latitude,
    longitude = longitude,
    state = state,
    country = country,
)
