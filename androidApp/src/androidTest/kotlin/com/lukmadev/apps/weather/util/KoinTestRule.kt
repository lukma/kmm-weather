package com.lukmadev.apps.weather.util

import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import org.koin.core.module.Module

class KoinTestRule(private val module: Module) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        loadKoinModules(module)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        unloadKoinModules(module)
    }
}
