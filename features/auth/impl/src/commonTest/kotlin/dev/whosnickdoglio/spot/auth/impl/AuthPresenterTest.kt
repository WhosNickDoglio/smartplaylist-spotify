// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.impl.internal.CodeChallenge
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAccessTokenUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAuthorizationUrlUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.VerifyStateUseCase
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.TestingCoroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.testing.auth.FakeSpotifyAccountService
import dev.whosnickdoglio.spot.rest.testing.auth.FakeSpotifyTokenProvider
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class AuthPresenterTest {

    @Test
    fun `initial state from AuthPresenter is unauthorized with no error message`() = runTest {
        val presenter = createPresenter()
        presenter.test {
            val state = awaitItem()
            assertThat(state.authState).isEqualTo(AuthorizationState.Unauthorized)
        }
    }

    private fun createPresenter(
        screen: AuthScreen = AuthScreen(),
        navigator: Navigator = FakeNavigator(screen),
        requestAuthorizationUrlUseCase: RequestAuthorizationUrlUseCase =
            createRequestAuthorizationUrlUseCase(),
        verifyStateUseCase: VerifyStateUseCase = createVerifyStateUseCase(),
        requestAccessTokenUseCase: RequestAccessTokenUseCase = createRequestAccessTokenUseCase(),
        tokenRepository: SpotifyTokenProvider = FakeSpotifyTokenProvider(),
    ): AuthPresenter =
        AuthPresenter(
            screen,
            navigator,
            requestAuthorizationUrlUseCase,
            verifyStateUseCase,
            requestAccessTokenUseCase,
            tokenRepository,
        )

    private fun createRequestAuthorizationUrlUseCase(
        codeChallenge: CodeChallenge = CodeChallenge { "" },
        spotifyAccountService: SpotifyAccountService = FakeSpotifyAccountService(),
        state: String = "",
        coroutineContextProvider: CoroutineContextProvider = TestingCoroutineContextProvider(),
    ): RequestAuthorizationUrlUseCase =
        RequestAuthorizationUrlUseCase(
            codeChallenge,
            spotifyAccountService,
            state,
            coroutineContextProvider,
        )

    private fun createVerifyStateUseCase(
        state: String = "",
        coroutineContextProvider: CoroutineContextProvider = TestingCoroutineContextProvider(),
    ): VerifyStateUseCase = VerifyStateUseCase(state, coroutineContextProvider)

    private fun createRequestAccessTokenUseCase(
        codeVerifier: String = "",
        spotifyAccountService: SpotifyAccountService = FakeSpotifyAccountService(),
        coroutineContextProvider: CoroutineContextProvider = TestingCoroutineContextProvider(),
    ): RequestAccessTokenUseCase =
        RequestAccessTokenUseCase(codeVerifier, spotifyAccountService, coroutineContextProvider)
}
