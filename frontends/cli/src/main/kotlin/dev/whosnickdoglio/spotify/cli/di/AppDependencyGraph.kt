// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.cli.di

import dev.whosnickdoglio.spotify.cli.RootCommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
internal interface AppDependencyGraph {
    val rootCommand: RootCommand
}
