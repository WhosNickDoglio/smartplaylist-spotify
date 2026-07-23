// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

public interface TokenRepository {

    public fun putTokens(tokens: Tokens)

    public fun getTokens(): Tokens?
}

public data class Tokens(
    val accessToken: String,
    val scope: String,
    val expiresIn: Int,
    val refreshToken: String,
)
