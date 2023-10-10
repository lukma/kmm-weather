package com.lukmadev.core

import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.di.commonModule
import com.lukmadev.core.di.databaseModule
import com.lukmadev.core.di.networkModule
import com.lukmadev.core.di.platformModule
import com.lukmadev.core.di.useCaseModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    appConfig: AppConfig,
    appDeclaration: KoinAppDeclaration,
) = startKoin {
    appDeclaration()
    modules(
        listOf(
            commonModule(appConfig),
            platformModule,
            databaseModule,
            networkModule,
            useCaseModule,
        )
    )
}
