// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import assertk.assertThat
import assertk.assertions.isEqualTo
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import kotlin.test.Test

class DefaultSpotifyAccountServiceTest {

    @Test
    fun test() {
        val service = createSpotifyAccountService()
        val url = service.getAuthUrl("state", "code")
        assertThat(url)
            .isEqualTo(
                "https://accounts.spotify.com/" +
                    "authorize?client_id=client_id&response_type=code&state=state" +
                    "&scope=playlist-modify-public playlist-modify-private user-follow-read " +
                    "user-read-recently-played&redirect_uri=https://spot/auth/callback" +
                    "&code_challenge_method=S256&code_challenge=code"
            )
    }

    private fun createSpotifyAccountService(
        clientId: String = "client_id",
        mockEngine: MockEngine = MockEngine { error("no-op") },
    ): SpotifyAccountService =
        DefaultSpotifyAccountService(clientId = clientId, httpClient = HttpClient(mockEngine))
}
