// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.github.michaelbull.result.Err
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.test.FakeNavigator
import com.slack.circuit.test.test
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.impl.internal.domain.AccessTokenFailure
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAccessTokenUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAuthorizationUrlUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.VerifyStateUseCase
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
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
            RequestAuthorizationUrlUseCase {
                ""
            },
        verifyStateUseCase: VerifyStateUseCase = VerifyStateUseCase { false },
        requestAccessTokenUseCase: RequestAccessTokenUseCase = RequestAccessTokenUseCase {
            Err(AccessTokenFailure.Error)
        },
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
}
