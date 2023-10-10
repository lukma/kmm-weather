package com.lukmadev.apps.weather.initializer

import android.content.Context
import androidx.startup.Initializer
import com.lukmadev.apps.weather.BuildConfig
import com.lukmadev.apps.weather.di.viewModelModule
import com.lukmadev.core.config.APIGateway
import com.lukmadev.core.config.AppConfig
import com.lukmadev.core.initKoin
import org.koin.android.ext.koin.androidContext

class KoinInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        val appConfig = AppConfig(
            weatherAPIGateway = APIGateway(
                host = BuildConfig.WEATHER_API_HOST,
                prefixPath = BuildConfig.WEATHER_API_PREFIX_PATH,
            )
        )
        initKoin(appConfig) {
            androidContext(context)
            modules(
                listOf(
                    viewModelModule,
                )
            )
        }
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}
