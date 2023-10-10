package com.lukmadev.core.data.geocoding.network

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/geo/1.0/direct")
internal class GeocodingResource {

    @Serializable
    @Resource("")
    class List(
        val parent: GeocodingResource = GeocodingResource(),
        val limit: Int = LIMIT,
        val q: String?,
    )

    companion object {
        private const val LIMIT = 5
    }
}
