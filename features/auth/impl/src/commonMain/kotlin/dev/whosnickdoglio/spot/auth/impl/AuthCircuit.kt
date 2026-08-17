// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import com.slack.circuit.runtime.CircuitUiState

internal interface AuthCircuit {

    sealed interface State : CircuitUiState {
        val eventSink: (Event) -> Unit

        data class Unauthorized(override val eventSink: (Event) -> Unit) : State

        data class Error(
            val message: String,
            override val eventSink: (Event) -> Unit,
        ) : State
    }

    sealed interface Event {
        data object LaunchAuth : Event
    }
}
