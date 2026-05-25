// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.web

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.whosnickdoglio.spot.web.di.WebDependencyGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.createGraph

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    val component by lazy { createGraph<WebDependencyGraph>() }
    ComposeViewport { CircuitCompositionLocals(component.circuit) { CircuitContent(DummyScreen) } }
}

internal data object DummyScreen : Screen {
    data class State(val hello: String) : CircuitUiState
}

@CircuitInject(DummyScreen::class, AppScope::class)
@Composable
internal fun DummyScreen(state: DummyScreen.State, modifier: Modifier = Modifier) {
    Column(modifier) { Text(state.hello) }
}

@CircuitInject(DummyScreen::class, AppScope::class)
@Composable
internal fun DummyPresenter(): DummyScreen.State = DummyScreen.State("Hello world!")
