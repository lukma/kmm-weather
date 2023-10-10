package com.lukmadev.core.di

import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.data.common.network.http.SimpleHttpClient
import io.ktor.client.plugins.*
import io.ktor.http.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    single(named(HttpClientQualifier.Main.Simple)) {
        SimpleHttpClient(get(), get()) {
            defaultRequest {
                host = get<AppConfig>().weatherAPIGateway.host
                url.protocol = URLProtocol.HTTPS
                if (get<AppConfig>().weatherAPIGateway.prefixPath.isNotEmpty()) {
                    url.encodedPath = get<AppConfig>().weatherAPIGateway.prefixPath
                }
            }
        }
    }
}
