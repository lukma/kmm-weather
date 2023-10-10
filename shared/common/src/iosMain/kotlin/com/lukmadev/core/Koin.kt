package com.lukmadev.core

import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.domain.product.usecase.GetProductsUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

fun initKoin(appConfig: AppConfig) {
    initKoin(appConfig) {}
}

object CommonDependencies : KoinComponent {
    // Product
    val getProductsUseCase: GetProductsUseCase get() = get()
}
