// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.AuthenticationState
import dev.whosnickdoglio.spot.auth.SpotifyAuthenticator
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(HomeScreen::class, AppScope::class)
@Composable
internal fun HomeScreen(state: HomeScreen.State, modifier: Modifier = Modifier) {
    CircuitContent(
        screen =
            when (state) {
                is HomeScreen.State.Authenticated -> state.screen
                is HomeScreen.State.Unauthenticated -> state.screen
            },
        modifier = modifier,
    )
}

@CircuitInject(HomeScreen::class, AppScope::class)
@Inject
internal class HomePresenter(private val authenticator: SpotifyAuthenticator) :
    Presenter<HomeScreen.State> {

    @Composable
    override fun present(): HomeScreen.State {
        val authenticationState by authenticator.authenticationState.collectAsState()

        val state =
            when (authenticationState) {
                AuthenticationState.AUTHENTICATED -> HomeScreen.State.Authenticated(PlaylistScreen)
                AuthenticationState.UNAUTHENTICATED -> HomeScreen.State.Unauthenticated(AuthScreen)
            }

        return state
    }
}
