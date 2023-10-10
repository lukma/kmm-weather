package com.lukmadev.core.data.geocoding.network.dto

import com.lukmadev.core.domain.geocoding.City
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CityDTO(
    @SerialName("name")
    val name: String,
    @SerialName("lat")
    val latitude: Double,
    @SerialName("lon")
    val longitude: Double,
    @SerialName("state")
    val state: String? = null,
    @SerialName("country")
    val country: String,
)

internal fun CityDTO.toCity() = City(
    name = name,
    latitude = latitude,
    longitude = longitude,
    state = state,
    country = country,
)
