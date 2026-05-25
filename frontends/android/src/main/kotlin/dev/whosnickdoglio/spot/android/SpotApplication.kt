// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Application
import dev.whosnickdoglio.spot.android.di.AndroidDependencyGraph
import dev.whosnickdoglio.spot.android.di.GraphOwner
import dev.zacsweers.metro.createGraph

public class SpotApplication: Application(), GraphOwner {
    override val graph: AndroidDependencyGraph by lazy {
        createGraph()
    }
}
