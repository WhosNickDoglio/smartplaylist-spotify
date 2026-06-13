// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
@file:JvmName("Main")

package dev.whosnickdoglio.spot

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.subcircuit.LocalSubCircuit
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.whosnickdoglio.spot.di.DesktopDependencyGraph
import dev.whosnickdoglio.spot.home.HomeScreen
import dev.zacsweers.metro.createGraph

public fun main(): Unit = application {
    val component = remember { createGraph<DesktopDependencyGraph>() }

    Window(onCloseRequest = ::exitApplication, title = "Smartplaylist Spotify") {
        SpotTheme {
            CircuitCompositionLocals(component.circuit) {
                CompositionLocalProvider(LocalSubCircuit provides component.subCircuit) {
                    val navStack = rememberSaveableNavStack(root = HomeScreen)
                    val navigator = rememberCircuitNavigator(navStack = navStack, onRootPop = {})
                    NavigableCircuitContent(navigator = navigator, navStack = navStack)
                }
            }
        }
    }
}
