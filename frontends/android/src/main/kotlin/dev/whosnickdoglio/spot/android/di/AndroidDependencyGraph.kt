// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android.di

import android.content.Context
import com.slack.circuit.foundation.Circuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
public interface AndroidDependencyGraph {
    public val circuit: Circuit
}


public interface GraphOwner {
    public val graph: AndroidDependencyGraph
}

internal fun Context.injector(): AndroidDependencyGraph = (applicationContext as GraphOwner).graph
