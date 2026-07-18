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
import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.screen.CircuitSaveable
import com.slack.circuit.runtime.screen.ProvideCircuitSaver
import com.slack.circuit.serialization.SerializableCircuitSaver
import com.slack.circuit.subcircuit.LocalSubCircuit
import com.slack.circuit.subcircuit.SubCircuit
import com.slack.circuitx.android.rememberAndroidScreenAwareNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import com.slack.circuitx.navigation.intercepting.rememberInterceptingNavigator
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.whosnickdoglio.spot.settings.SettingsScreen
import dev.whosnickdoglio.spot.url.LaunchUrlScreen
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

private val saver =
    SerializableCircuitSaver(
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(CircuitSaveable::class) {
                    subclass(AuthScreen::class)
                    subclass(CreateScreen::class)
                    subclass(LaunchUrlScreen::class)
                    subclass(PlaylistScreen::class)
                    subclass(SettingsScreen::class)
                }
            }
        }
    )

@AssistedInject
internal class App(
    private val circuit: Circuit,
    private val subCircuit: SubCircuit,
    private val navigationInterceptors: Set<NavigationInterceptor>,
    @Assisted private val context: Context
) {
    @AssistedFactory
    fun interface Factory {
        fun create(context: Context): App
    }

    @Composable
    operator fun invoke() {
        SpotTheme {
            Surface {
                CircuitCompositionLocals(circuit) {
                    CompositionLocalProvider(LocalSubCircuit provides subCircuit) {
                        ProvideCircuitSaver(saver) {
                            val navStack = rememberSaveableNavStack(root = PlaylistScreen)
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
