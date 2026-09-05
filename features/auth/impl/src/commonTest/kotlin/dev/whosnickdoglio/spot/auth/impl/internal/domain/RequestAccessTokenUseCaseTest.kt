// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl.internal.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.assert.hasErrorValue
import dev.whosnickdoglio.spot.assert.hasOkayValue
import dev.whosnickdoglio.spot.assert.isError
import dev.whosnickdoglio.spot.assert.isOkay
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.coroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.auth.TokenRequestResponse
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.whosnickdoglio.spot.rest.testing.auth.FakeSpotifyAccountService
import kotlin.test.Test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class RequestAccessTokenUseCaseTest {

    @Test
    fun `invoke RequestAccessTokenUseCase with correct code and codeVerifier returns success result`() =
        runTest {
            val useCase =
                createUseCase(
                    spotifyAccountService =
                        FakeSpotifyAccountService(
                            accessToken = { _, _ ->
                                ApiResult.success(
                                    TokenRequestResponse(
                                        accessToken = "accessToken",
                                        tokenType = "Bearer",
                                        scope = "scope",
                                        expiresIn = 100,
                                        refreshToken = "",
                                    )
                                )
                            }
                        )
                )

            val result = useCase("code")

            assertThat(result).isOkay()
            assertThat(result)
                .hasOkayValue()
                .isEqualTo(
                    Tokens(
                        accessToken = "accessToken",
                        scope = "scope",
                        expiresIn = 100,
                        refreshToken = "",
                    )
                )
        }

    @Test
    fun `invoke RequestAccessTokenUseCase with incorrect code or verifier returns failure result`() =
        runTest {
            val useCase =
                createUseCase(
                    spotifyAccountService =
                        FakeSpotifyAccountService(accessToken = { _, _ -> ApiResult.apiFailure() })
                )

            val result = useCase("code")

            assertThat(result).isError()
            assertThat(result).hasErrorValue().isEqualTo(AccessTokenFailure.Error)
        }

    private fun TestScope.createUseCase(
        codeVerifier: String = "",
        spotifyAccountService: SpotifyAccountService = FakeSpotifyAccountService(),
        coroutineContextProvider: CoroutineContextProvider = coroutineContextProvider(),
    ): RequestAccessTokenUseCase =
        RequestAccessTokenUseCase(
            codeVerifier,
            spotifyAccountService,
            coroutineContextProvider,
        )
}
