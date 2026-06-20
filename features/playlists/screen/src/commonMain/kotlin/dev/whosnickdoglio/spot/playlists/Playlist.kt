// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.playlists

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.slack.circuit.codegen.annotations.CircuitInject
import dev.zacsweers.metro.AppScope
import kotlin.time.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@CircuitInject(PlaylistScreen::class, AppScope::class)
@Composable
internal fun PlaylistScreen(state: PlaylistScreen.State, modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { state.eventSink(PlaylistScreen.Event.CreateNewPlaylist) }
            ) {
                Text("Create")
            }
        },
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.playlists) { playlist -> PlaylistItem(playlist, modifier.padding(it)) }
        }
    }
}

@Composable
private fun PlaylistItem(playlist: Playlist, modifier: Modifier = Modifier) {
    ListItem(
        headlineContent = { Text(playlist.title) },
        overlineContent = { Text("Synced 10/10/26 11am") },
        trailingContent = { Text("SYNC") },
        supportingContent = {
            Text("Music from your followed artists released in the last 30 days")
        },
        modifier = modifier.wrapContentSize(),
    )
}

@Preview
@Composable
private fun PlaylistItemPreview() {
    PlaylistItem(
        playlist =
            Playlist(
                id = "",
                title = "Last 30 days",
                lastSync = Clock.System.now().toLocalDateTime(TimeZone.UTC),
            )
    )
}
