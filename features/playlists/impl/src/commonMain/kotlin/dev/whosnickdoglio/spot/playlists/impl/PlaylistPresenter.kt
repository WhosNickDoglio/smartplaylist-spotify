// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists.impl

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.whosnickdoglio.spot.rest.api.PlaylistSpotifyService
import dev.whosnickdoglio.spot.rest.api.RequestPlaylistResponse
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.auth.isAuthenticated
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlin.time.Clock
import kotlin.uuid.Uuid
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@AssistedInject
internal class PlaylistPresenter(
    private val tokenRepository: SpotifyTokenProvider,
    private val playlistSpotifyService: PlaylistSpotifyService,
    @Assisted private val navigator: Navigator,
) : Presenter<PlaylistCircuit.State> {

    @CircuitInject(PlaylistScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(navigator: Navigator): PlaylistPresenter
    }

    @Composable
    override fun present(): PlaylistCircuit.State {
        var errorMessage by remember { mutableStateOf<String?>(null) }
        // TODO figure out a better way to do this
        LaunchedEffect(Unit) {
            if (!tokenRepository.getTokens().isAuthenticated()) {
                navigator.goTo(AuthScreen(bounceBack = PlaylistScreen))
            }
        }

        val realPlaylists: List<Playlist> by
            produceState(emptyList()) {
                when (val result = playlistSpotifyService.requestPlaylists()) {
                    is ApiResult.Failure -> {
                        errorMessage = "Oops no music!"
                        value = emptyList()
                    }
                    is ApiResult.Success<RequestPlaylistResponse> -> {
                        val rawResponse = result.value
                        val playlists =
                            rawResponse.items.orEmpty().map {
                                Playlist(
                                    it.id ?: Uuid.random().toString(),
                                    title = it.name ?: Uuid.random().toString(),
                                    lastSync = Clock.System.now().toLocalDateTime(TimeZone.UTC),
                                )
                            }

                        value = playlists
                    }
                }
            }

        return PlaylistCircuit.State(playlists = realPlaylists, errorMessage = errorMessage) { event
            ->
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
