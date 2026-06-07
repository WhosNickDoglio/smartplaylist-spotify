// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists

import androidx.compose.runtime.Composable
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@AssistedInject
internal class PlaylistPresenter(@Assisted private val navigator: Navigator) :
    Presenter<PlaylistScreen.State> {

    @CircuitInject(PlaylistScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): PlaylistPresenter
    }

    @Composable
    override fun present(): PlaylistScreen.State {
        val playlists =
            List(100) {
                Playlist(
                    id = it.toString(),
                    title = "My playlist $it",
                    lastSync = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                )
            }

        return PlaylistScreen.State(playlists = playlists) { event ->
            when (event) {
                is PlaylistScreen.Event.CreateNewPlaylist -> {
                    navigator.goTo(CreateScreen)
                }
                is PlaylistScreen.Event.EditPlaylist -> TODO()
                is PlaylistScreen.Event.DeletePlaylist -> TODO()
                is PlaylistScreen.Event.SyncAllLivePlaylists -> TODO()
                is PlaylistScreen.Event.SyncPlaylist -> TODO()
            }
        }
    }
}
