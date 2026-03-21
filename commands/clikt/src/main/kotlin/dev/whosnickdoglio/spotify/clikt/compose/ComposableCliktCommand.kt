// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.clikt.compose

import androidx.compose.runtime.Composable
import com.github.ajalt.clikt.core.BaseCliktCommand
import com.github.ajalt.clikt.parsers.CommandLineParser

public abstract class ComposableCliktCommand : BaseCliktCommand<ComposableCliktCommand>() {
    @Composable public abstract fun run()
}

@Composable
public fun ComposableCliktCommand.parse(argv: Array<String>) {
    CommandLineParser.parseAndRun(this, argv.asList()) {}

    //    CommandLineParser.parseAndRun(this, argv.asList()) { flows += it.run() }
    //    return flow { flows.forEach { emitAll(it) } }
}

@Composable
public fun ComposableCliktCommand.main(argv: Array<String>) {
    //    return runMosaicBlocking {}
    return CommandLineParser.mainReturningValue(this) { parse(argv) }
}
