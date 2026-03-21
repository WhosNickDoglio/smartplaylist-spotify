// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.subcommands
import dev.whosnickdoglio.spotify.commands.RootSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@RootSubcommand
@ContributesIntoSet(AppScope::class)
public class SpotifyAuthCommand(@AuthSubcommand commands: Set<SuspendingCliktCommand>) :
    SuspendingCliktCommand(name = "auth") {
    init {
        subcommands(commands)
    }

    override fun help(context: Context): String {
        return "AUTH"
    }

    override suspend fun run(): Unit = Unit
}
