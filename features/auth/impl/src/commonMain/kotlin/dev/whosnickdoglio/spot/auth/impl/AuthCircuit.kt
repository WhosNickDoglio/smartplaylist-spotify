// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import com.slack.circuit.runtime.CircuitUiState

internal interface AuthCircuit {
    data class State(
        val authState: AuthorizationState,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    sealed interface Event {
        data object LaunchAuth : Event
    }
}
