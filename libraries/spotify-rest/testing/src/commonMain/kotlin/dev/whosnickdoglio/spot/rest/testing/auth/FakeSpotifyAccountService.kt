// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.rest.testing.auth

import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.auth.TokenRequestResponse

public class FakeSpotifyAccountService(
    private val authUrl: (state: String, challenge: String) -> String = { _, _ -> "" },
    private val accessToken:
        suspend (code: String, verifier: String) -> ApiResult<
                TokenRequestResponse,
                SpotifyErrorResponse,
            > =
        { _, _ ->
            error("no-op")
        },
) : SpotifyAccountService {
    override fun getAuthUrl(state: String, codeChallenge: String): String =
        authUrl(state, codeChallenge)

    override suspend fun requestAccessToken(
        code: String,
        codeVerifier: String,
    ): ApiResult<TokenRequestResponse, SpotifyErrorResponse> = accessToken(code, codeVerifier)
}
