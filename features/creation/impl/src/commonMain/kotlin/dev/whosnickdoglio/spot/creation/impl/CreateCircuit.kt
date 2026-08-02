// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.creation.impl

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

public data object CreateCircuit {
    public data class State(val oops: String, val eventSink: (Event) -> Unit) : CircuitUiState

    public sealed interface Event {
        public data class EditPlaylistTile(val title: String) : Event

        public data class AddRule(val rule: Rule) : Event

        public data class RemoveRule(val rule: Rule) : Event

        public data object ToggleLiveUpdates : Event

        public data object CreatePlaylist : Event

        public data object DeletePlaylist : Event
    }
}

public class Rule

@CircuitInject(CreateScreen::class, AppScope::class)
@Composable
internal fun CreateScreen(state: CreateCircuit.State, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) { Text(state.oops) }
}

@CircuitInject(CreateScreen::class, AppScope::class)
@Inject
internal class CreatePresenter : Presenter<CreateCircuit.State> {

    @Composable
    override fun present(): CreateCircuit.State =
        CreateCircuit.State("Hello world!") { event ->
            when (event) {
                is CreateCircuit.Event.AddRule -> TODO()
                is CreateCircuit.Event.CreatePlaylist -> TODO()
                is CreateCircuit.Event.RemoveRule -> TODO()
                is CreateCircuit.Event.EditPlaylistTile -> TODO()
                is CreateCircuit.Event.ToggleLiveUpdates -> TODO()
                is CreateCircuit.Event.DeletePlaylist -> TODO()
            }
        }
}
