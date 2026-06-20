// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import androidx.annotation.CheckResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen

@Stable
public class DesktopScreenAwareNavigator(
    private val delegate: Navigator,
    private val starter: DesktopScreenStarter,
) : Navigator by delegate {
    override fun goTo(screen: Screen): Boolean {
        return when (screen) {
            is DesktopScreen -> starter.start(screen)
            else -> delegate.goTo(screen)
        }
    }
}

@CheckResult
@Composable
public fun rememberDesktopScreenAwareNavigator(
    delegate: Navigator,
    starter: DesktopScreenStarter,
): Navigator = remember(delegate) { DesktopScreenAwareNavigator(delegate, starter) }

public fun interface DesktopScreenStarter {
    public fun start(screen: DesktopScreen): Boolean
}
