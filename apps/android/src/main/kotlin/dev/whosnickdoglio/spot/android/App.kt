// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.content.Context
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.screen.CircuitSaver
import com.slack.circuit.runtime.screen.ProvideCircuitSaver
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.subcircuit.LocalSubCircuit
import com.slack.circuit.subcircuit.SubCircuit
import com.slack.circuitx.android.rememberAndroidScreenAwareNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject

@AssistedInject
internal class App(
    private val circuit: Circuit,
    private val subCircuit: SubCircuit,
    private val navigationInterceptors: Set<NavigationInterceptor>,
    private val circuitSaver: CircuitSaver,
    @Assisted private val context: Context,
    @Assisted private val backstack: List<Screen>,
) {
    @AssistedFactory
    fun interface Factory {
        fun create(
            context: Context,
            backstack: List<Screen>,
        ): App
    }

    @Composable
    operator fun invoke() {
        SpotTheme {
            Surface {
                CircuitCompositionLocals(circuit) {
                    CompositionLocalProvider(LocalSubCircuit provides subCircuit) {
                        ProvideCircuitSaver(circuitSaver) {
                            val navStack = rememberSaveableNavStack(initialScreens = backstack)
                            val baseNavigator =
                                rememberAndroidScreenAwareNavigator(
                                    rememberCircuitNavigator(navStack),
                                    context,
                                )

                            val navigator =
                                rememberInterceptingNavigator(
                                    navigator = baseNavigator,
                                    interceptors = navigationInterceptors.toList(),
                                )
                            NavigableCircuitContent(
                                navigator = navigator,
                                navStack = navStack,
                                modifier = Modifier.safeDrawingPadding(),
                                decoratorFactory =
                                    remember(navigator) {
                                        GestureNavigationDecorationFactory()
                                    },
                            )
                        }
                    }
                }
            }
        }
    }
}
