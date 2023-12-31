package com.lukmadev.core.util

import com.lukmadev.core.data.common.network.http.SimpleHttpClient
import com.lukmadev.core.di.commonModule
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.mockk.mockk
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

interface HttpClientTest : KoinTest {

    @BeforeTest
    fun setup() {
        val networkModule = module {
            includes(commonModule(mockk()))
            single { SimpleHttpClient(MockEngine { respondOk() }, get()) }
        }
        startKoin {
            modules(networkModule)
        }
    }

    @AfterTest
    fun tearDown() {
        stopKoin()
    }

    fun HttpClient.mockResponse(body: String, status: HttpStatusCode, headers: Headers? = null) {
        with((engine as MockEngine).config.requestHandlers) {
            removeFirst()
            add {
                respond(
                    content = ByteReadChannel(body),
                    status = status,
                    headers = headers ?: headersOf(HttpHeaders.ContentType, "application/json"),
                )
            }
        }
    }
}
