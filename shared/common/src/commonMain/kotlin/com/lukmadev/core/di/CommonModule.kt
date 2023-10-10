package com.lukmadev.core.di

import com.lukmadev.core.config.AppConfig
import kotlinx.serialization.json.Json
import org.koin.dsl.module

internal fun commonModule(appConfig: AppConfig) = module {
    single { appConfig }

    single {
        Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}
