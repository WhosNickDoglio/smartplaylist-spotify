// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.core.net.toUri
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.android.rememberAndroidScreenAwareNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.LaunchUrlScreen
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
public class MainActivity(private val circuit: Circuit) : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotTheme {
                Surface {
                    CircuitCompositionLocals(circuit) {
                        val navStack = rememberSaveableNavStack(root = AuthScreen)
                        val delegate = rememberCircuitNavigator(navStack)
                        val urlAwareNavigator = remember {
                            UrlAwareNavigator(
                                delegate,
                                launchUrl = {
                                    CustomTabsIntent.Builder().build().launchUrl(this, it.toUri())
                                },
                            )
                        }
                        val navigator =
                            rememberAndroidScreenAwareNavigator(
                                urlAwareNavigator,
                                this@MainActivity,
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

private class UrlAwareNavigator(
    private val delegate: Navigator,
    private val launchUrl: (String) -> Unit,
) : Navigator by delegate {
    override fun goTo(screen: Screen): Boolean =
        when (screen) {
            is LaunchUrlScreen -> {
                launchUrl(screen.url)
                true
            }
            else -> {
                delegate.goTo(screen)
            }
        }
}
