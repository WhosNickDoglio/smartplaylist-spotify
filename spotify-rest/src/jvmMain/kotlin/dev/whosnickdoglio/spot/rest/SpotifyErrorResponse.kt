// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

public sealed interface SpotifyErrorResponse {
    // 400
    public data class BadRequest(val oops: String) : SpotifyErrorResponse

    // 401
    public data class BadToken(val oops: String) : SpotifyErrorResponse

    // 403
    public data class BadOauthRequest(val oops: String) : SpotifyErrorResponse

    // 429
    public data class ExceededRateLimit(val oops: String) : SpotifyErrorResponse
}
