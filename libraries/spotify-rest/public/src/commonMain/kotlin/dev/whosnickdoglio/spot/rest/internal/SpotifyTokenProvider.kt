// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

// persistence
public interface SpotifyTokenProvider {

    public suspend fun getTokens(): String

    public suspend fun putTokens(access: String, refresh: String)
}

@ContributesBinding(AppScope::class)
internal class SpotifyTokenProviderImpl : SpotifyTokenProvider {
    override suspend fun getTokens(): String {
        TODO("Not yet implemented")
    }

    override suspend fun putTokens(access: String, refresh: String) {
        TODO("Not yet implemented")
    }
}
