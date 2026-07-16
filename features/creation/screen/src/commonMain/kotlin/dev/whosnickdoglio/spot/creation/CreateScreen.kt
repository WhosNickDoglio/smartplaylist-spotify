// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.creation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
public data object CreateScreen : Screen {
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
internal fun CreateScreen(state: CreateScreen.State, modifier: Modifier = Modifier) {
    Column(modifier.fillMaxSize()) { Text(state.oops) }
}

@CircuitInject(CreateScreen::class, AppScope::class)
@Inject
internal class CreatePresenter : Presenter<CreateScreen.State> {

    @Composable
    override fun present(): CreateScreen.State =
        CreateScreen.State("Hello world!") { event ->
            when (event) {
                is CreateScreen.Event.AddRule -> TODO()
                is CreateScreen.Event.CreatePlaylist -> TODO()
                is CreateScreen.Event.RemoveRule -> TODO()
                is CreateScreen.Event.EditPlaylistTile -> TODO()
                is CreateScreen.Event.ToggleLiveUpdates -> TODO()
                is CreateScreen.Event.DeletePlaylist -> TODO()
            }
        }
}
