// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import dev.whosnickdoglio.spotify.auth.command.di.AuthSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import se.michaelthelin.spotify.SpotifyApi
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials

@Inject
@AuthSubcommand
@ContributesIntoSet(AppScope::class)
public class LoginCommand(private val spotifyApi: SpotifyApi) : SuspendingCliktCommand("login") {

    override fun help(context: Context): String = "Attempts to log in into Spotify"

    override suspend fun run() {
        echo("Attempting to authorize Spotify...")
        val clientCredentials = spotifyApi.clientCredentials().build().execute()
        val token = clientCredentials.accessToken
        echo("Successfully authorized Spotify!")

        val login: AuthorizationCodeCredentials? =
            spotifyApi
                .authorizationCodePKCE(/* code= */ token, /* code_verifier= */ "foo")
                .build()
                .execute()
        echo("Successfully logged in! ${login.toString()}")
    }
}
