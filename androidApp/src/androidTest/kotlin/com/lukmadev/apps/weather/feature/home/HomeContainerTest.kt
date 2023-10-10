package com.lukmadev.apps.weather.feature.home

import androidx.compose.ui.test.junit4.createComposeRule
import com.lukmadev.apps.weather.di.viewModelModule
import com.lukmadev.apps.weather.util.KoinTestRule
import org.junit.Rule
import org.koin.dsl.module

class HomeContainerTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val koinTestRule = KoinTestRule(
        module = module {
            includes(viewModelModule)
        }
    )
}
