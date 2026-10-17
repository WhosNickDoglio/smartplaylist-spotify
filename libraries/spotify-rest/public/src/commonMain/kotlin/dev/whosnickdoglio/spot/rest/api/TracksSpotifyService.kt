// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.api

public interface TracksSpotifyService {
    // https://developer.spotify.com/documentation/web-api/reference/get-users-saved-tracks
    public suspend fun requestSavedTracks()

    // https://developer.spotify.com/documentation/web-api/reference/get-an-albums-tracks
    public suspend fun requestAlbumsTracks()
}
