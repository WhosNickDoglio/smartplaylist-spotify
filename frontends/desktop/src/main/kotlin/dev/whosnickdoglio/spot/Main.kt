// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
@file:JvmName("Main")

package dev.whosnickdoglio.spot

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.whosnickdoglio.spot.di.DesktopDependencyGraph
import dev.zacsweers.metro.createGraph

public fun main(): Unit = application {
    val component = remember { createGraph<DesktopDependencyGraph>() }

    Window(onCloseRequest = ::exitApplication, title = "Smartplaylist Spotify") {
        CircuitCompositionLocals(component.circuit) { SpotTheme { CircuitContent(AuthScreen) } }
    }
}
