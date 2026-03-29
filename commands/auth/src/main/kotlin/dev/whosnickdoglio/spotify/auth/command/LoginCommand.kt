// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import dev.whosnickdoglio.spotify.auth.command.di.AuthSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.future.await
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials

@Inject
@AuthSubcommand
@ContributesIntoSet(AppScope::class)
public class LoginCommand(private val spotifyApi: SpotifyApi) : SuspendingCliktCommand("login") {

    override fun help(context: Context): String = "Attempts to log in into Spotify"

    override suspend fun run() {
        echo("Attempting to authorize Spotify...")
        val result: ClientCredentials? =
            spotifyApi.clientCredentials().build().executeAsync().await()
        echo(result.toString())
    }
}
