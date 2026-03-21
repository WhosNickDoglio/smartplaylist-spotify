// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.cli

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.command.main
import com.github.ajalt.clikt.core.Context
import com.github.ajalt.clikt.core.subcommands
import dev.whosnickdoglio.spotify.cli.di.AppDependencyGraph
import dev.whosnickdoglio.spotify.cli.di.RootSubcommand
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraph

public suspend fun main(args: Array<String>) {
    val graph = createGraph<AppDependencyGraph>()
    graph.rootCommand.main(args)
}

@Inject
internal class RootCommand(@RootSubcommand commands: Set<SuspendingCliktCommand>) :
    SuspendingCliktCommand(name = "sps-cli") {
    init {
        subcommands(commands.sortedBy { it.commandName })
    }

    override fun help(context: Context): String = "Spotify CLI for creating and managing playlists."

    override suspend fun run(): Unit = Unit
}
