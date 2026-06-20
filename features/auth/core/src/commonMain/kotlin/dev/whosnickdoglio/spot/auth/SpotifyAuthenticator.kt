// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

import dev.whosnickdoglio.spot.auth.internal.CodeChallenge
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

public interface SpotifyAuthenticator {
    public val authenticationState: StateFlow<AuthenticationState>

    public suspend fun auth(): AuthResponse
}

public enum class AuthenticationState {
    AUTHENTICATED,
    UNAUTHENTICATED,
}

public sealed interface AuthResponse {
    public data object Success : AuthResponse

    public data class Error(val message: String) : AuthResponse
}

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
internal class DefaultSpotifyAuthenticator(
    private val codeChallenge: CodeChallenge,
    private val spotifyAccountService: SpotifyAccountService,
) : SpotifyAuthenticator {
    private val _authState = MutableStateFlow(AuthenticationState.UNAUTHENTICATED)

    override val authenticationState: StateFlow<AuthenticationState> = _authState.asStateFlow()

    override suspend fun auth(): AuthResponse {
        val challenge = codeChallenge.challenge()

        // val url = "https://accounts.spotify.com/authorize"

        val response = spotifyAccountService.getAuthUrl(state = "zyx", codeChallenge = challenge)

        return AuthResponse.Success
    }
}
