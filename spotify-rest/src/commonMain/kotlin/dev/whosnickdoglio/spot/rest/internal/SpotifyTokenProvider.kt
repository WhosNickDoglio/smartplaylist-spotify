// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

public interface SpotifyTokenProvider {
    // TODO refresh vs access
    // suspend?
    // persistence
    public suspend fun token(): String
}

@ContributesBinding(AppScope::class)
internal class SpotifyTokenProviderImpl : SpotifyTokenProvider {
    override suspend fun token(): String {
        TODO("Not yet implemented")
    }
}
