// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json

@ContributesTo(AppScope::class)
public interface HttpClientProvider {

    @SingleIn(AppScope::class)
    @Provides
    public fun provideHttpClient(): HttpClient =
        HttpClient(CIO) {
            install(ContentNegotiation) { json() }
            install(Logging) { level = LogLevel.ALL }
        }
}
