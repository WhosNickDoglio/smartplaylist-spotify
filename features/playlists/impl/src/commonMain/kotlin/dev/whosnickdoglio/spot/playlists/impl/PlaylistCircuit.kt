// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists.impl

import com.slack.circuit.runtime.CircuitUiState
import kotlinx.datetime.LocalDateTime

public data object PlaylistCircuit {
    public data class State(val playlists: List<Playlist>, val eventSink: (Event) -> Unit) :
        CircuitUiState

    public sealed interface Event {

        public data object SyncAllLivePlaylists : Event

        public data object CreateNewPlaylist : Event

        public data class EditPlaylist(val id: String) : Event

        public data class DeletePlaylist(val id: String) : Event

        public data class SyncPlaylist(val id: String) : Event
    }
}

public data class Playlist(val id: String, val title: String, val lastSync: LocalDateTime)
