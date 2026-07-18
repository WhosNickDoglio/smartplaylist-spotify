// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import android.content.Intent
import androidx.core.net.toUri
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.android.IntentScreen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.InterceptedResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class LaunchUrlInterceptor: NavigationInterceptor {
    override fun goTo(
        screen: Screen,
        navigationContext: NavigationContext
    ): InterceptedResult = if (screen is LaunchUrlScreen) {
        InterceptedGoToResult.Rewrite(
            IntentScreen(Intent(Intent.ACTION_VIEW, screen.url.toUri()))
        )
    } else {
        NavigationInterceptor.Skipped
    }
}
