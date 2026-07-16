// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.retained.CircuitRetainedSettings
import com.slack.circuit.retained.ExperimentalCircuitRetainedApi
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
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
public class MainActivity(
    private val circuit: Circuit,
    private val subCircuit: SubCircuit,
    private val navigationInterceptors: Set<NavigationInterceptor>,
) : ComponentActivity() {

    private val saver = SerializableCircuitSaver(
        SavedStateConfiguration {
            serializersModule = SerializersModule {
                polymorphic(CircuitSaveable::class) {
                    subclass(AuthScreen::class)
                    subclass(CreateScreen::class)
                    subclass(LaunchUrlScreen::class)
                    subclass(PlaylistScreen::class)
                    subclass(SettingsScreen::class )
                }
            }
        }
    )

    @OptIn(ExperimentalCircuitRetainedApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        CircuitRetainedSettings.useFirstParty = true
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotTheme {
                Surface {
                    CircuitCompositionLocals(circuit) {
                        CompositionLocalProvider(LocalSubCircuit provides subCircuit) {
                            ProvideCircuitSaver(saver) {
                                val navStack = rememberSaveableNavStack(root = PlaylistScreen)
                                val baseNavigator =
                                    rememberAndroidScreenAwareNavigator(
                                        rememberCircuitNavigator(navStack),
                                        this@MainActivity,
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
                                            GestureNavigationDecorationFactory(
                                                // onBackInvoked = navigator::pop
                                            )
                                        },
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
