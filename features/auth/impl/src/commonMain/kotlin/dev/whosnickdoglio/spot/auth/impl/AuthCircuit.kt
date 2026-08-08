// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import com.slack.circuit.runtime.CircuitUiState

public interface AuthCircuit {
    public data class State(
        val authState: AuthorizationState,
        val errorMessage: String?,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    public sealed interface Event {
        public data object LaunchAuth : Event
    }
}
