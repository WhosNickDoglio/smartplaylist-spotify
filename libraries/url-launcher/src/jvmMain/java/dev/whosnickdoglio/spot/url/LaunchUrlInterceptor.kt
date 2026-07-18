// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.InterceptedResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import io.github.solcott.kmp.parcelize.Parcelize
import java.awt.Desktop
import java.net.URI
import java.util.Locale
import kotlinx.serialization.Serializable

@ContributesIntoSet(AppScope::class)
internal class LaunchUrlInterceptor : NavigationInterceptor {
    override fun goTo(screen: Screen, navigationContext: NavigationContext): InterceptedResult =
        if (screen is LaunchUrlScreen) {
            InterceptedGoToResult.Rewrite(OpenUrlDesktop)
        } else {
            NavigationInterceptor.Skipped
        }
}

@Parcelize public interface DesktopScreen : Screen

@Serializable public data object OpenUrlDesktop : Screen

// https://stackoverflow.com/a/68426773
private fun openInBrowser(uri: URI) {
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
