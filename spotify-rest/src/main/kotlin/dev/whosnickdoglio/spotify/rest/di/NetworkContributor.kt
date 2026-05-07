// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.rest.di

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.SpotifyHttpManager

@ContributesTo(AppScope::class)
public interface NetworkContributor {

    @Provides
    public fun provideSpotifyApi(
        @ClientId clientId: String,
        @ClientSecret clientSecret: String,
    ): SpotifyApi =
        SpotifyApi.Builder()
            .setClientId(clientId)
            .setClientSecret(clientSecret)
            .setRedirectUri(
                SpotifyHttpManager.makeUri("https://whosnickdoglio.dev/smartplaylist-cli/callback")
            )
            .build()
}
