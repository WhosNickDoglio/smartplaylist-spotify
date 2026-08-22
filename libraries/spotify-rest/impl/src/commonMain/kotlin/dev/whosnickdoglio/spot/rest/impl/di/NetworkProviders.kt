// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.di

import com.livewire.plugin.network.ktor.LivewireNetworkPlugin
import com.slack.eithernet.successOrNull
import dev.whosnickdoglio.spot.info.BuildInfo
import dev.whosnickdoglio.spot.info.BuildVariant
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.auth.isAuthenticated
import dev.whosnickdoglio.spot.rest.auth.toTokens
import dev.whosnickdoglio.spot.rest.impl.CLIENT_ID
import dev.whosnickdoglio.spot.rest.impl.CLIENT_SECRET
import dev.whosnickdoglio.spot.rest.impl.auth.requestRefreshToken
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
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
    public fun provideHttpClient(
        buildInfo: BuildInfo,
        tokenProvider: SpotifyTokenProvider,
        @ClientId clientId: String,
    ): HttpClient =
        HttpClient(CIO) {
            if (buildInfo.buildVariant == BuildVariant.DEBUG) {
                install(Logging) {
                    logger = Logger.ANDROID
                    level = LogLevel.ALL
                }

                install(LivewireNetworkPlugin)
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
                    loadTokens {
                        val tokens = tokenProvider.getTokens()
                        if (!tokens.isAuthenticated()) return@loadTokens null
                        BearerTokens(
                            accessToken = tokens.accessToken,
                            refreshToken = tokens.refreshToken,
                        )
                    }
                    refreshTokens {
                        val oldToken = oldTokens?.refreshToken ?: return@refreshTokens null
                        val response =
                            client.requestRefreshToken(
                                refreshToken = oldToken,
                                clientId = clientId,
                            )

                        val tokens = response.successOrNull()
                        if (tokens != null) {
                            tokenProvider.putTokens(tokens.toTokens())
                            BearerTokens(
                                accessToken = tokens.accessToken,
                                refreshToken = tokens.refreshToken,
                            )
                        } else {
                            null
                        }
                    }
                    sendWithoutRequest { request ->
                        // Return false for endpoints that don't need auth (login, register)
                        !request.url.pathSegments.contains("account")
                    }
                }
            }
        }
}
