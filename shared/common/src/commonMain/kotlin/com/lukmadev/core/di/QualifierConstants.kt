package com.lukmadev.core.di

internal object HttpClientQualifier {
    object Main {
        const val Simple = "SimpleMainHttpClient"
    }
}

internal object DataSourceQualifier {
    const val Local = "LocalDataSource"
    const val Network = "NetworkDataSource"
}

internal object DatabaseQualifier {
    object TableAdapter {
        const val FavoriteCity = "FavoriteCity"
    }
}
