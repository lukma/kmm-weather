package com.lukmadev.core.data.common.dto

import com.lukmadev.core.domain.common.exception.ClientRequestError
import com.lukmadev.core.domain.common.exception.ErrorCode
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ErrorDTO(
    @SerialName("error")
    val error: Error,
) {

    @Serializable
    internal data class Error(
        @SerialName("code")
        val code: ErrorCode,
        @SerialName("message")
        val message: String,
    )
}

internal fun ErrorDTO.toApiError() = with(error) {
    ClientRequestError(
        code = code,
        message = message,
    )
}
