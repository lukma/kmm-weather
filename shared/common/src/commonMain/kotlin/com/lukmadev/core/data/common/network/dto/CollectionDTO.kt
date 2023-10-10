package com.lukmadev.core.data.common.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CollectionDTO<T>(
    @SerialName("list")
    val items: List<T>,
)
