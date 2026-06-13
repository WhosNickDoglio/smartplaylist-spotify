// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import io.github.solcott.kmp.parcelize.Parcelize

@Parcelize
public class SettingsScreen : Screen {
    public data class State(val oops: String, val eventSink: (Event) -> Unit) : CircuitUiState

    public sealed interface Event
}

public class Rule

@CircuitInject(SettingsScreen::class, AppScope::class)
@Composable
internal fun SettingsScreen(state: SettingsScreen.State, modifier: Modifier = Modifier) {
    Column(modifier) { Text(state.oops) }
    LazyColumn {}
}

@CircuitInject(SettingsScreen::class, AppScope::class)
@Inject
internal class SettingsPresenter : Presenter<SettingsScreen.State> {

    @Composable
    override fun present(): SettingsScreen.State = SettingsScreen.State("Hello world!") { event -> }
}
