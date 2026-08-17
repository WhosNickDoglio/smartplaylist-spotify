// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.auth

import kotlinx.serialization.Serializable

// persistence
public interface SpotifyTokenProvider {

    public suspend fun getTokens(): Tokens

    public suspend fun putTokens(tokens: Tokens)
}

public fun TokenRequestResponse.toTokens(): Tokens =
    Tokens(
        accessToken = this.accessToken,
        scope = this.scope,
        expiresIn = this.expiresIn,
        refreshToken = this.refreshToken,
    )

@Serializable
public data class Tokens(
    val accessToken: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String,
)

public fun Tokens.isAuthenticated(): Boolean =
    accessToken.isNotEmpty() && scope.isNotEmpty() && refreshToken.isNotEmpty() && expiresIn != 0
