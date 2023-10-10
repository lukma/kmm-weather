package com.lukmadev.apps.weather.util

import io.mockk.mockk
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module

class KoinTestRule(private val module: Module) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        stopKoin()
        startKoin {
            androidContext(mockk(relaxed = true))
            modules(module)
        }
    }
}
