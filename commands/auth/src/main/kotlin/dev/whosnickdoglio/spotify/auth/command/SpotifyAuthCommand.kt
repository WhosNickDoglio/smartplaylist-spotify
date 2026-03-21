// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import dev.whosnickdoglio.spotify.commands.RootSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@RootSubcommand
@ContributesIntoSet(AppScope::class)
public class SpotifyAuthCommand : SuspendingCliktCommand(name = "auth") {
    override suspend fun run() {
        TODO("Not yet implemented")
    }
}
