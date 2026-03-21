// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.rest

public interface SpotifyService {

    public suspend fun followedArtists()

    public suspend fun albumsByArtist()

    public suspend fun playlists()

    public suspend fun createPlaylist()

    public suspend fun updatePlaylist()
}
