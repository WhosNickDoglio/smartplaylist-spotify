// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.home

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen

public data object HomeScreen : Screen {
    public sealed interface State : CircuitUiState {
        public data object Authenticated : State

        public data object Unauthenticated : State
    }
}
