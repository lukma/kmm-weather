package com.lukmadev.core.domain.geocoding

data class City(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val state: String?,
    val country: String,
)
