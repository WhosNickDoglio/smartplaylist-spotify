// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl.internal.domain

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.spot.auth.impl.internal.CodeChallenge
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.coroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.testing.auth.FakeSpotifyAccountService
import kotlin.test.Test
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest

class RequestAuthorizationUrlUseCaseTest {

    @Test
    fun `hello`() = runTest {
        val useCase =
            createUseCase(
                spotifyAccountService =
                    FakeSpotifyAccountService(
                        authUrl = { state, challenge ->
                            "$state $challenge"
                        }
                    )
            )

        val url = useCase()
        assertThat(url).isEqualTo("state challenge")
    }

    private fun TestScope.createUseCase(
        challenge: CodeChallenge = CodeChallenge { "challenge" },
        spotifyAccountService: SpotifyAccountService = FakeSpotifyAccountService(),
        state: String = "state",
        coroutineContextProvider: CoroutineContextProvider = coroutineContextProvider(),
    ): RequestAuthorizationUrlUseCase =
        RequestAuthorizationUrlUseCase(
            codeChallenge = challenge,
            spotifyAccountService = spotifyAccountService,
            state = state,
            coroutineContextProvider = coroutineContextProvider,
        )
}
