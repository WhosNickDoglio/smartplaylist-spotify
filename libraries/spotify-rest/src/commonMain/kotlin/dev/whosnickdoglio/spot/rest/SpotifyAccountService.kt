// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

import com.eygraber.uri.Uri
import com.slack.eithernet.ApiResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO error handling
public interface SpotifyAccountService {

    public fun getAuthUrl(state: String, codeChallenge: String): Uri

    public suspend fun requestAccessToken(
        code: String,
        codeVerifier: String,
    ): ApiResult<AccessTokenRequestResponse, Unit>
}

@Serializable public data class AuthorizeResponse(val code: String, val state: String)

@Serializable
public data class AccessTokenRequestResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String = "Bearer",
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String,
)
