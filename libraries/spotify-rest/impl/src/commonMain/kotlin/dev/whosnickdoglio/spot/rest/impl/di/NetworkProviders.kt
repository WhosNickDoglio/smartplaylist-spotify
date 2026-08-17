// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.di

import com.livewire.plugin.network.ktor.LivewireNetworkPlugin
import com.slack.eithernet.integration.ktor.apiResultOf
import com.slack.eithernet.successOrNull
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.info.BuildInfo
import dev.whosnickdoglio.spot.info.BuildVariant
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.auth.TokenRequestResponse
import dev.whosnickdoglio.spot.rest.auth.toTokens
import dev.whosnickdoglio.spot.rest.impl.CLIENT_ID
import dev.whosnickdoglio.spot.rest.impl.auth.DefaultSpotifyAccountService
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
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters
import io.ktor.serialization.kotlinx.json.json
import kotlin.coroutines.ContinuationInterceptor
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.serialization.json.Json

@ContributesTo(AppScope::class)
public interface NetworkProviders {

    @Provides @ClientId public fun provideClientId(): String = CLIENT_ID

    // https://www.kmpbits.com/posts/ktor-client-advanced
    @SingleIn(AppScope::class)
    @Provides
    public fun provideHttpClient(
        buildInfo: BuildInfo,
        tokenProvider: SpotifyTokenProvider,
        coroutineContextProvider: CoroutineContextProvider,
        @ClientId clientId: String,
    ): HttpClient =
        HttpClient(CIO) {
            engine {
                this.dispatcher =
                    coroutineContextProvider.io[ContinuationInterceptor] as CoroutineDispatcher
            }

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
                        if (tokens == null) {
                            return@loadTokens null
                        } else {
                            BearerTokens(
                                accessToken = tokens.accessToken,
                                refreshToken = tokens.refreshToken,
                            )
                        }
                    }
                    refreshTokens {
                        val oldToken = oldTokens?.refreshToken ?: return@refreshTokens null
                        val response =
                            client.apiResultOf<TokenRequestResponse, SpotifyErrorResponse> {
                                client.submitForm(
                                    "https://${DefaultSpotifyAccountService.BASE_URL}/api/token",
                                    formParameters =
                                        parameters {
                                            append("grant_type", "refresh_token")
                                            append("refresh_token", value = oldToken)
                                            append("client_id", value = clientId)
                                        },
                                )
                            }

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
