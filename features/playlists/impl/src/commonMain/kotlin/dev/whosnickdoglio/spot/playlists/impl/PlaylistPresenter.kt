// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists.impl

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@AssistedInject
internal class PlaylistPresenter(@Assisted private val navigator: Navigator) :
    Presenter<PlaylistCircuit.State> {

    @CircuitInject(PlaylistScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): PlaylistPresenter
    }

    @Composable
    override fun present(): PlaylistCircuit.State {
        val playlists =
            List(100) {
                Playlist(
                    id = it.toString(),
                    title = "My playlist $it",
                    lastSync = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                )
            }

        return PlaylistCircuit.State(playlists = playlists) { event ->
            when (event) {
                is PlaylistCircuit.Event.CreateNewPlaylist -> {
                    navigator.goTo(CreateScreen)
                }
                is PlaylistCircuit.Event.EditPlaylist -> TODO()
                is PlaylistCircuit.Event.DeletePlaylist -> TODO()
                is PlaylistCircuit.Event.SyncAllLivePlaylists -> TODO()
                is PlaylistCircuit.Event.SyncPlaylist -> TODO()
            }
        }
    }
}
