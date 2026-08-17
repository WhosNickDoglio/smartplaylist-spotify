// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.api

public interface AlbumsSpotifyService {
    // https://developer.spotify.com/documentation/web-api/reference/get-users-saved-albums
    public suspend fun requestSavedAlbums()

    // https://developer.spotify.com/documentation/web-api/reference/get-an-artists-albums
    public suspend fun requestArtistsAlbums()
}
