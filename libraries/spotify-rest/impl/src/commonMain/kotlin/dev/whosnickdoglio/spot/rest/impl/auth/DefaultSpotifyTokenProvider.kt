// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.rest.impl.auth

import dev.whosnickdoglio.spot.rest.internal.SpotifyTokenProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class DefaultSpotifyTokenProvider : SpotifyTokenProvider {
    override suspend fun getTokens(): String {
        TODO("Not yet implemented")
    }

    override suspend fun putTokens(access: String, refresh: String) {
        TODO("Not yet implemented")
    }
}
