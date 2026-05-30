// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.subcommands
import dev.whosnickdoglio.spotify.auth.command.di.AuthSubcommand
import dev.whosnickdoglio.spotify.clikt.NoOpSuspendingCliktCommand
import dev.whosnickdoglio.spotify.commands.RootSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.binding

@Inject
@RootSubcommand
@ContributesIntoSet(AppScope::class, binding = binding<SuspendingCliktCommand>())
public class RootAuthCommand(@AuthSubcommand commands: Set<SuspendingCliktCommand>) :
    NoOpSuspendingCliktCommand(name = "auth") {
    init {
        subcommands(commands.sortedBy { it.commandName })
    }

    override fun help(context: Context): String = "Command for managing authentication with Spotify"
}
