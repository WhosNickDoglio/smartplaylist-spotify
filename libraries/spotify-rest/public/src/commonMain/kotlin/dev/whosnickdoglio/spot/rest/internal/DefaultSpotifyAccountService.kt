// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import com.slack.eithernet.ApiResult
import com.slack.eithernet.integration.ktor.apiResultOf
import dev.whosnickdoglio.spot.rest.AccessTokenRequestResponse
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import dev.whosnickdoglio.spot.rest.di.ClientId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.URLProtocol
import io.ktor.http.buildUrl
import io.ktor.http.parameters
import io.ktor.http.path
import io.ktor.util.appendAll

@ContributesBinding(AppScope::class)
internal class DefaultSpotifyAccountService(
    @param:ClientId private val clientId: String,
    private val httpClient: HttpClient,
) : SpotifyAccountService {

    // https://developer.spotify.com/documentation/web-api/concepts/scopes
    private val scopes =
        listOf(
                "playlist-modify-public",
                "playlist-modify-private",
                "user-follow-read",
                "user-read-recently-played",
            )
            .joinToString(separator = " ")

    // https://accounts.spotify.com/authorize
    override fun getAuthUrl(state: String, codeChallenge: String): String = buildUrl {
        protocol = URLProtocol.HTTPS
        host = BASE_URL
        path("authorize")
        encodedParameters.appendAll(
            "client_id" to clientId,
            "response_type" to "code",
            "state" to state,
            "scope" to scopes,
            "redirect_uri" to REDIRECT_URL,
            "code_challenge_method" to "S256",
            "code_challenge" to codeChallenge,
        )
    }
        .toString()

    // https://accounts.spotify.com/api/token
    override suspend fun requestAccessToken(
        code: String,
        codeVerifier: String,
    ): ApiResult<AccessTokenRequestResponse, SpotifyErrorResponse> = httpClient.apiResultOf {
        submitForm(
            "https://${BASE_URL}/api/token",
            formParameters =
                parameters {
                    append("grant_type", "authorization_code")
                    append("code", code)
                    append("redirect_uri", REDIRECT_URL)
                    append("client_id", clientId)
                    append("code_verifier", codeVerifier)
                },
        )
    }

    private companion object {
        private const val BASE_URL = "accounts.spotify.com"
        private const val REDIRECT_URL = "https://spot/auth/callback"
    }
}
