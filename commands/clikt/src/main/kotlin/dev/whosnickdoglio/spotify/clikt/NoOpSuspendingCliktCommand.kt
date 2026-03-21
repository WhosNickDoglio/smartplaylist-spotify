// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.clikt

import com.github.ajalt.clikt.command.SuspendingCliktCommand

public open class NoOpSuspendingCliktCommand(name: String) : SuspendingCliktCommand(name) {
    final override suspend fun run(): Unit = Unit
}
