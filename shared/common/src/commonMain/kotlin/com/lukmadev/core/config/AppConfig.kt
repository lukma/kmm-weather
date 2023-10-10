package com.lukmadev.core.config

data class AppConfig(
    val weatherAPIGateway: APIGateway,
)

data class APIGateway(
    val host: String,
    val prefixPath: String,
)
