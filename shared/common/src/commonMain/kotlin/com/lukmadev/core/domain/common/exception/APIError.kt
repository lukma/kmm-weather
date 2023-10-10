package com.lukmadev.core.domain.common.exception

sealed class APIError(override val message: String) : Throwable(message)

data class DatabaseError(override val cause: Throwable) : APIError(cause.message.toString())

data class SerializationError(override val cause: Throwable) : APIError(cause.message.toString())

data class ClientRequestError(
    val code: ErrorCode,
    override val message: String,
) : APIError(message)

data class ServerResponseError(
    override val message: String,
    val status: Int,
) : APIError(message)
