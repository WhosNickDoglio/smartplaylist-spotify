// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@CircuitInject(PlaylistScreen::class, AppScope::class)
@Composable
internal fun PlaylistScreen(state: PlaylistScreen.State, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { state.eventSink(PlaylistScreen.Event.CreateNewPlaylist) }
            ) {
                Text("Create")
            }
        },
    ) {
        LazyColumn { items(state.playlists) { playlist -> PlaylistItem(playlist) } }
    }
}

@Composable
private fun PlaylistItem(playlist: Playlist, modifier: Modifier = Modifier) {
    Column(modifier) { Text(playlist.title) }
}

@Suppress("UnusedPrivateProperty")
@AssistedInject
internal class PlaylistPresenter(@Assisted private val navigator: Navigator) :
    Presenter<PlaylistScreen.State> {

    @CircuitInject(PlaylistScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): PlaylistPresenter
    }

    @Composable
    override fun present(): PlaylistScreen.State =
        PlaylistScreen.State(playlists = emptyList()) { event ->
            when (event) {
                is PlaylistScreen.Event.CreateNewPlaylist -> {
                    // navigator.goTo()
                }

                is PlaylistScreen.Event.EditPlaylist -> TODO()
                is PlaylistScreen.Event.DeletePlaylist -> TODO()
                is PlaylistScreen.Event.SyncAllLivePlaylists -> TODO()
                is PlaylistScreen.Event.SyncPlaylist -> TODO()
            }
        }
}
