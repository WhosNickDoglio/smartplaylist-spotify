// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import dev.whosnickdoglio.spot.android.di.AndroidDependencyGraph
import dev.whosnickdoglio.spot.android.di.GraphOwner
import dev.zacsweers.metro.createGraph

internal class SpotApplication: GraphOwner() {
    override val graph: AndroidDependencyGraph by lazy {
        createGraph()
    }
}
