package org.cenkeraydin.moviekmp.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.cenkeraydin.moviekmp.appContext
import org.cenkeraydin.moviekmp.data.local.DatabaseDriverFactory



actual fun provideHttpClient(): HttpClient = HttpClient {
    install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true }, contentType = ContentType.Any)
    }
}

actual fun provideDatabaseDriverFactory(): DatabaseDriverFactory {
    return DatabaseDriverFactory(appContext)
}