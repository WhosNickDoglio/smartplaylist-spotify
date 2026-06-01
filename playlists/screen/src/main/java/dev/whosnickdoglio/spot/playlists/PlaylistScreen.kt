// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists

import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import java.time.LocalDateTime

public data object PlaylistScreen : Screen {
    public data class State(val playlists: List<Playlist>, val eventSink: (Event) -> Unit) :
        CircuitUiState

    public sealed interface Event {
        public data object CreateNewPlaylist : Event

        public data class EditPlaylist(val id: String) : Event
    }
}

public data class Playlist(val id: String, val title: String, val lastSync: LocalDateTime)
