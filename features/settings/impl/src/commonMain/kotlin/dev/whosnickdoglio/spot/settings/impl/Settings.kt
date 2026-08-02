// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.settings.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.settings.SettingsScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

public class SettingsCircuit {
    public data class State(val oops: String, val eventSink: (Event) -> Unit) : CircuitUiState

    public sealed interface Event
}

@CircuitInject(SettingsScreen::class, AppScope::class)
@Composable
internal fun SettingsScreen(state: SettingsCircuit.State, modifier: Modifier = Modifier) {
    Column(modifier) { Text(state.oops) }
    LazyColumn {}
}

@CircuitInject(SettingsScreen::class, AppScope::class)
@Inject
internal class SettingsPresenter : Presenter<SettingsCircuit.State> {

    @Composable
    override fun present(): SettingsCircuit.State =
        SettingsCircuit.State("Hello world!") { event -> }
}
