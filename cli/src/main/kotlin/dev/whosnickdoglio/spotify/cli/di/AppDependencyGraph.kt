// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.cli.di

import dev.whosnickdoglio.spotify.CLIENT_ID
import dev.whosnickdoglio.spotify.CLIENT_SECRET
import dev.whosnickdoglio.spotify.cli.RootCommand
import dev.whosnickdoglio.spotify.rest.di.ClientId
import dev.whosnickdoglio.spotify.rest.di.ClientSecret
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides

@DependencyGraph(AppScope::class)
internal interface AppDependencyGraph {
    val rootCommand: RootCommand

    @Provides @ClientSecret fun provideClientSecret(): String = CLIENT_SECRET

    @Provides @ClientId fun provideClientId(): String = CLIENT_ID
}
