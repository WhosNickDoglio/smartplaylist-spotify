// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.di

import dev.whosnickdoglio.spot.rest.CLIENT_ID
import dev.whosnickdoglio.spot.rest.CLIENT_SECRET
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
public interface NetworkProviders {

    @Provides @ClientSecret public fun provideClientSecret(): String = CLIENT_SECRET

    @Provides @ClientId public fun provideClientId(): String = CLIENT_ID

    // https://www.kmpbits.com/posts/ktor-client-advanced
    @SingleIn(AppScope::class)
    @Provides
    public fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                    }
                )
            }

            install(Auth) {
                bearer {
                    // loadTokens {}
                    // refreshTokens {}
                    sendWithoutRequest { request ->
                        // Return false for endpoints that don't need auth (login, register)
                        !request.url.pathSegments.contains("account")
                    }
                }
            }
        }
}
