// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.livewire

import com.livewire.client.LivewireClient
import com.livewire.plugin.database.DatabasePlugin
import com.livewire.plugin.network.NetworkPlugin
import com.livewire.plugin.recomposition.RecompositionPlugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
@BindingContainer
public object LivewireProvider {

    @Provides
    public fun provideLiveWireClient(databasePlugin: DatabasePlugin): LivewireClient =
        LivewireClient {
            install(NetworkPlugin())
            install(RecompositionPlugin())
            install(databasePlugin)
        }
}
