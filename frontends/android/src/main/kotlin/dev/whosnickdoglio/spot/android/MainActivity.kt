// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.slack.circuit.foundation.Circuit
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuitx.android.rememberAndroidScreenAwareNavigator
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import dev.whosnickdoglio.spot.design.SpotTheme
import dev.whosnickdoglio.spot.home.HomeScreen
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
                // TODO Parcelable nonsense
                CircuitCompositionLocals(circuit) {
                    val navStack = rememberSaveableNavStack(root = HomeScreen)
                    val navigator =
                        rememberAndroidScreenAwareNavigator(
                            rememberCircuitNavigator(navStack),
                            this@MainActivity,
                        )
                    NavigableCircuitContent(
                        navigator = navigator,
                        navStack = navStack,
                        decoratorFactory =
                            remember(navigator) {
                                GestureNavigationDecorationFactory(onBackInvoked = navigator::pop)
                            },
                    )
                }
            }
        }
    }
}
