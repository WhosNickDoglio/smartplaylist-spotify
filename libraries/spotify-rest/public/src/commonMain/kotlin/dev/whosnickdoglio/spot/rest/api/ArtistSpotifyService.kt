// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.api

public interface ArtistSpotifyService {

    // https://developer.spotify.com/documentation/web-api/reference/get-followed
    public suspend fun requestFollowedArtists()
}
