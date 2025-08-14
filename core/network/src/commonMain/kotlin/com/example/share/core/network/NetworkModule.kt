package com.example.share.core.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val networkModule = module {
    single<ApiClient> { ApiClient(get()) }
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(json = Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
            }
            defaultRequest {
                url(NetworkConfig.BASE_URL)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
                connectTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
                socketTimeoutMillis = NetworkConfig.TIMEOUT_MILLIS
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
        }
    }

}