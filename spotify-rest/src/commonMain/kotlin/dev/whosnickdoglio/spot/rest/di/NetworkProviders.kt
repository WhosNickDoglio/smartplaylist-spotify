// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.di

import dev.whosnickdoglio.spotify.CLIENT_ID
import dev.whosnickdoglio.spotify.CLIENT_SECRET
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json

@ContributesTo(AppScope::class)
public interface NetworkProviders {

    @Provides @ClientSecret public fun provideClientSecret(): String = CLIENT_SECRET

    @Provides @ClientId public fun provideClientId(): String = CLIENT_ID

    @SingleIn(AppScope::class)
    @UnauthorizedClient
    @Provides
    public fun provideUnauthorizedHttpClient(): HttpClient =
        HttpClient(CIO) { install(ContentNegotiation) { json() } }

    @SingleIn(AppScope::class)
    @AuthorizedClient
    @Provides
    public fun provideAuthorizedHttpClient(
        // spotifyAuthenticator: Authenticator,
        // authInterceptor: Interceptor,
    ): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
            install(Auth) { bearer {} }
        }
}
