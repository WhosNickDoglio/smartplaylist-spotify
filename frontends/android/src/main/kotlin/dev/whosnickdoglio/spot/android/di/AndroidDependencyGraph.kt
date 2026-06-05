// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android.di

import android.app.Application
import android.content.Context
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
internal interface AndroidDependencyGraph {
    val circuit: Circuit
}


internal abstract class GraphOwner: Application() {
    internal abstract val graph: AndroidDependencyGraph
}

internal fun Context.injector(): AndroidDependencyGraph = (applicationContext as GraphOwner).graph
