// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope

public data object AuthScreen : Screen {
    public data class State(val hello: String) : CircuitUiState
}

@CircuitInject(AuthScreen::class, AppScope::class)
@Composable
internal fun AuthScreen(state: AuthScreen.State, modifier: Modifier = Modifier) {
    Column(modifier) { Text(state.hello) }
}

@CircuitInject(AuthScreen::class, AppScope::class)
@Composable
internal fun AuthPresenter(): AuthScreen.State = AuthScreen.State("Hello world!")
