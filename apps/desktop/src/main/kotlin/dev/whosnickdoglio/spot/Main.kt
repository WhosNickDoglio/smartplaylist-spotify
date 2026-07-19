// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
@file:JvmName("Main")

package dev.whosnickdoglio.spot

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.retained.CircuitRetainedSettings
import com.slack.circuit.retained.ExperimentalCircuitRetainedApi
import com.slack.circuit.subcircuit.LocalSubCircuit
import com.slack.circuit.subcircuit.SubCircuit
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.whosnickdoglio.spot.di.DesktopDependencyGraph
import dev.whosnickdoglio.spot.url.rememberDesktopScreenAwareNavigator
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraph

@OptIn(ExperimentalCircuitRetainedApi::class)
public fun main(): Unit = application {
    CircuitRetainedSettings.useFirstParty = true
    val component = remember { createGraph<DesktopDependencyGraph>() }
    Window(onCloseRequest = ::exitApplication, title = "Smartplaylist Spotify") { component.app() }
}

@Inject
internal class App(
    private val circuit: Circuit,
    private val subCircuit: SubCircuit,
    private val navigationInterceptors: Set<NavigationInterceptor>,
) {

    @Composable
    operator fun invoke() {
        SpotTheme {
            CircuitCompositionLocals(circuit) {
                CompositionLocalProvider(LocalSubCircuit provides subCircuit) {
                    Surface {
                        val navStack = rememberSaveableNavStack(root = AuthScreen)
                        val baseNavigator =
                            rememberDesktopScreenAwareNavigator(
                                rememberCircuitNavigator(navStack = navStack, onRootPop = {})
                            )
                        val navigator =
                            rememberInterceptingNavigator(
                                navigator = baseNavigator,
                                interceptors = navigationInterceptors.toList(),
                            )
                        NavigableCircuitContent(navigator = navigator, navStack = navStack)
                    }
                }
            }
        }
    }
}
