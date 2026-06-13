// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.di

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.subcircuit.SubCircuit
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.DependencyGraph

@DependencyGraph(AppScope::class)
internal interface DesktopDependencyGraph {
    val circuit: Circuit
    val subCircuit: SubCircuit
}
