// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import com.slack.eithernet.ApiResult
import com.slack.eithernet.integration.ktor.apiResultOf
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import dev.whosnickdoglio.spot.rest.auth.TokenRequestResponse
import dev.whosnickdoglio.spot.rest.impl.auth.DefaultSpotifyAccountService.Companion.BASE_URL
import io.ktor.client.HttpClient
import io.ktor.client.request.forms.submitForm
import io.ktor.http.parameters

internal suspend fun HttpClient.requestRefreshToken(
    refreshToken: String,
    clientId: String,
): ApiResult<TokenRequestResponse, SpotifyErrorResponse> {
    return apiResultOf {
        submitForm(
            "https://${BASE_URL}/api/token",
            formParameters =
                parameters {
                    append("grant_type", "refresh_token")
                    append("refresh_token", refreshToken)
                    append("client_id", clientId)
                },
        )
    }
}
