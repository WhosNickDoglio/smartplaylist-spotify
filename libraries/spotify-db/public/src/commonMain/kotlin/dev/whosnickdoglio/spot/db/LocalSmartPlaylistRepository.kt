// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.db

public interface LocalSmartPlaylistRepository {

    public suspend fun getPlaylists(): List<SmartPlaylist>

    public suspend fun createPlaylist(
        name: String,
        id: String,
        url: String?,
    )
}
