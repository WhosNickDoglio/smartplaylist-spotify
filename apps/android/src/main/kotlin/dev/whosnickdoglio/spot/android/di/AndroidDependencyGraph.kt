// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android.di

import android.content.Context
import com.livewire.client.LivewireClient
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders

@DependencyGraph(AppScope::class)
internal interface AndroidDependencyGraph : MetroAppComponentProviders {
    val livewireClient: LivewireClient

    @DependencyGraph.Factory
    fun interface Factory {
        fun create(@Provides context: Context): AndroidDependencyGraph
    }
}
