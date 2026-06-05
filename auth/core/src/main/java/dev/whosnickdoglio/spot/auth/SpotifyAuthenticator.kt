// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.auth.internal.CodeChallenge
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.di.ClientId
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.createGraph
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

public interface SpotifyAuthenticator {
    public val authenticationState: StateFlow<AuthenticationState>

    public suspend fun auth()
}

public enum class AuthenticationState {
    AUTHENTICATED,
    UNAUTHENTICATED,
}

@DependencyGraph(AppScope::class)
public interface TestGraph {
    public val auth: SpotifyAuthenticator
}

@ContributesBinding(AppScope::class)
internal class DefaultSpotifyAuthenticator(
    private val codeChallenge: CodeChallenge,
    private val spotifyAccountService: SpotifyAccountService,
    @param:ClientId private val clientId: String,
) : SpotifyAuthenticator {
    private val _authState = MutableStateFlow(AuthenticationState.UNAUTHENTICATED)

    override val authenticationState: StateFlow<AuthenticationState> = _authState.asStateFlow()

    override suspend fun auth() {
        val challenge = codeChallenge.challenge()

        // val url = "https://accounts.spotify.com/authorize"

        val response =
            spotifyAccountService.authorize(
                clientId = clientId,
                state = "zyx",
                scope = "",
                codeChallenge = challenge,
            )

        when (response) {
            is ApiResult.Failure.ApiFailure<*> -> println(response.error)
            is ApiResult.Failure.HttpFailure<*> -> println(response.error)
            is ApiResult.Failure.NetworkFailure -> println(response.error)
            is ApiResult.Failure.UnknownFailure -> println(response.error)
            is ApiResult.Success<*> -> println(response.value.toString())
        }
    }
}

public fun main(): Unit = runBlocking {
    val graph = createGraph<TestGraph>()
    graph.auth.auth()
}
