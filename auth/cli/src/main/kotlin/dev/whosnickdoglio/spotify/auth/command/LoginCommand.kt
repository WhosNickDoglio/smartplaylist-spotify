// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import dev.whosnickdoglio.spotify.auth.command.di.AuthSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@AuthSubcommand
@ContributesIntoSet(AppScope::class)
public class LoginCommand : SuspendingCliktCommand("login") {

    override fun help(context: Context): String = "Attempts to log in into Spotify"

    override suspend fun run() {
        echo("Attempting to authorize Spotify...")
    }
}
