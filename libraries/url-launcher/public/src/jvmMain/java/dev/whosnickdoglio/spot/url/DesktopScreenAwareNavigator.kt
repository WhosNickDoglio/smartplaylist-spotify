// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import androidx.annotation.CheckResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.screen.Screen
import java.awt.Desktop
import java.net.URI
import java.util.Locale

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

@Stable
private class DefaultDesktopScreenStarter : DesktopScreenStarter {
    override fun start(screen: DesktopScreen): Boolean =
        when (screen) {
            is OpenUrlDesktop -> {
                openInBrowser(URI(screen.url))
                true
            }
            else -> false
        }
}

@CheckResult
@Composable
public fun rememberDesktopScreenAwareNavigator(
    delegate: Navigator,
    starter: DesktopScreenStarter = DefaultDesktopScreenStarter(),
): Navigator = remember(delegate) { DesktopScreenAwareNavigator(delegate, starter) }

public fun interface DesktopScreenStarter {
    public fun start(screen: DesktopScreen): Boolean
}

// https://stackoverflow.com/a/68426773
internal fun openInBrowser(uri: URI) {

    val osName by
        lazy(LazyThreadSafetyMode.NONE) {
            System.getProperty("os.name").lowercase(Locale.getDefault())
        }
    val desktop = Desktop.getDesktop()
    when {
        Desktop.isDesktopSupported() && desktop.isSupported(Desktop.Action.BROWSE) ->
            desktop.browse(uri)
        "mac" in osName -> Runtime.getRuntime().exec("open $uri")
        "nix" in osName || "nux" in osName -> Runtime.getRuntime().exec("xdg-open $uri")
        else -> throw RuntimeException("cannot open $uri")
    }
}
