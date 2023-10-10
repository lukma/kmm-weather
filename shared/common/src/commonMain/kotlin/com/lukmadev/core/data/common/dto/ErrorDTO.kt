package com.lukmadev.core.data.common.dto

import com.lukmadev.core.domain.common.exception.ClientRequestError
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorDTO(
    @SerialName("cod")
    val code: String,
    @SerialName("message")
    val message: String,
)

internal fun ErrorDTO.toApiError() = ClientRequestError(
    code = code,
    message = message,
)
