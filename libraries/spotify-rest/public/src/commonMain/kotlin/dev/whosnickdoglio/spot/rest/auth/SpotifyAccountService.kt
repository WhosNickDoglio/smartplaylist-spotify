// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.auth

import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public interface SpotifyAccountService {

    public fun getAuthUrl(state: String, codeChallenge: String): String

    public suspend fun requestAccessToken(
        code: String,
        codeVerifier: String,
    ): ApiResult<TokenRequestResponse, SpotifyErrorResponse>
}

@Serializable public data class AuthorizeResponse(val code: String, val state: String)

@Serializable
public data class TokenRequestResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String = "Bearer",
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String,
)
