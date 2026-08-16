// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

// persistence
public interface SpotifyTokenProvider {

    public suspend fun getTokens(): String

    public suspend fun putTokens(access: String, refresh: String)
}
