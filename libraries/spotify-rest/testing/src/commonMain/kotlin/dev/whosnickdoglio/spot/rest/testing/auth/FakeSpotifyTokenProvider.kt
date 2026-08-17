// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.rest.testing.auth

import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.auth.Tokens

public class FakeSpotifyTokenProvider(private var tokens: Tokens? = null) : SpotifyTokenProvider {
    override suspend fun getTokens(): Tokens? = tokens

    override suspend fun putTokens(tokens: Tokens) {
        this.tokens = tokens
    }
}
