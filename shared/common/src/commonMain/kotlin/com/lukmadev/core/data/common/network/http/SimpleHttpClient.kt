package com.lukmadev.core.data.common.network.http

import com.lukmadev.core.data.common.network.dto.ErrorDTO
import com.lukmadev.core.data.common.network.dto.toApiError
import com.lukmadev.core.domain.common.exception.SerializationError
import com.lukmadev.core.domain.common.exception.ServerResponseError
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.plugins.resources.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@Suppress("FunctionName")
internal fun SimpleHttpClient(
    engine: HttpClientEngine,
    json: Json,
    block: HttpClientConfig<*>.() -> Unit = {},
): HttpClient = HttpClient(engine) {
    expectSuccess = true
    block.invoke(this)

    install(ContentNegotiation) {
        json(json)
    }
    install(Resources)
    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.ALL
    }

    HttpResponseValidator {
        handleResponseExceptionWithRequest { cause, _ ->
            throw when (cause) {
                is SerializationException -> cause.toAPIError()
                is ClientRequestException -> cause.toAPIError()
                is ServerResponseException -> cause.toAPIError()
                else -> cause
            }
        }
    }
}

private fun SerializationException.toAPIError() = SerializationError(this)

private suspend fun ClientRequestException.toAPIError() = runCatching {
    response.body<ErrorDTO>().toApiError()
}.getOrElse {
    (it as? SerializationException)?.toAPIError() ?: throw it
}

private fun ServerResponseException.toAPIError() = ServerResponseError(
    message = message,
    status = response.status.value,
)
