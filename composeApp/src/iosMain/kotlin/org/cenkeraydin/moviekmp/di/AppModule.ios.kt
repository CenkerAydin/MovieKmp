package org.cenkeraydin.moviekmp.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.darwin.Darwin
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.cenkeraydin.moviekmp.data.local.DatabaseDriverFactory
import org.koin.dsl.module

actual fun provideHttpClient(): HttpClient = HttpClient(Darwin) {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
    }
}

actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory() // in-memory veya file driver
}