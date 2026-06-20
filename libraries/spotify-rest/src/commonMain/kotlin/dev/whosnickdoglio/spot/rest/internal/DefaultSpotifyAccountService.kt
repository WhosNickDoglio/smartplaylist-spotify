// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import com.eygraber.uri.Uri
import com.slack.eithernet.ApiResult
import com.slack.eithernet.integration.ktor.apiResultOf
import dev.whosnickdoglio.spot.rest.AccessTokenRequestResponse
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.di.ClientId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters

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
    override fun getAuthUrl(state: String, codeChallenge: String): Uri =
        Uri.Builder()
            .scheme("https")
            .path(BASE_URL)
            .appendPath("authorize")
            .appendQueryParameter("client_id", clientId)
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("state", state)
            .appendQueryParameter("scope", scopes)
            .appendQueryParameter("redirect_uri", REDIRECT_URL)
            .appendQueryParameter("code_challenge_method", "S256")
            .appendQueryParameter("code_challenge", codeChallenge)
            .build()

    // https://accounts.spotify.com/api/token
    override suspend fun requestAccessToken(
        code: String,
        codeVerifier: String,
    ): ApiResult<AccessTokenRequestResponse, Unit> = httpClient.apiResultOf {
        submitForm(
            "$BASE_URL/api/token",
            formParameters =
                parameters {
                    append("grant_type", "authorization_code")
                    append("code", code)
                    append("redirectUri", REDIRECT_URL)
                    append("client_id", clientId)
                    append("code_verifier", codeVerifier)
                },
        )
    }

    private companion object {
        private const val BASE_URL = "accounts.spotify.com/"
        private const val REDIRECT_URL = "http://127.0.0.1:8080"
    }
}
