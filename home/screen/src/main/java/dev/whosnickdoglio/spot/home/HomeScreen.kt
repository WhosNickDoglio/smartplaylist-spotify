// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.home

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.playlists.PlaylistScreen

public data object HomeScreen : Screen {
    public sealed interface State : CircuitUiState {
        public data class Authenticated(val screen: PlaylistScreen) : State

        public data class Unauthenticated(val screen: AuthScreen) : State
    }
}
