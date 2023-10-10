package com.lukmadev.core.data.geocoding.network

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/geo/1.0/")
internal class GeocodingResource {

    @Serializable
    @Resource("direct")
    class Direct(
        val parent: GeocodingResource = GeocodingResource(),
        val limit: Int = LIMIT,
        val q: String?,
    )

    companion object {
        private const val LIMIT = 5
    }
}
