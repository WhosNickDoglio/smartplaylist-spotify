// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
@file:JvmName("Main")

package dev.whosnickdoglio.spot

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.whosnickdoglio.spot.di.DesktopDependencyGraph
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.createGraph

public fun main(): Unit = application {
    val component = remember { createGraph<DesktopDependencyGraph>() }

    Window(onCloseRequest = ::exitApplication, title = "Spotify") {
        CircuitCompositionLocals(component.circuit) { CircuitContent(DummyScreen) }
    }
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
