// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import com.slack.circuit.runtime.screen.Screen
import com.slack.circuitx.navigation.intercepting.InterceptedGoToResult
import com.slack.circuitx.navigation.intercepting.InterceptedResult
import com.slack.circuitx.navigation.intercepting.NavigationContext
import com.slack.circuitx.navigation.intercepting.NavigationInterceptor
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.ProtectedScreen
import dev.whosnickdoglio.spot.auth.TokenRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class AuthInterceptor(private val tokenRepository: TokenRepository) :
    NavigationInterceptor {
    override fun goTo(screen: Screen, navigationContext: NavigationContext): InterceptedResult {
        // For protected screens, verify authentication
        if (screen is ProtectedScreen && tokenRepository.getTokens() == null) {
            // Rewrite to login screen with original destination as a parameter
            // return InterceptedGoToResult.Rewrite(LoginScreen(afterLoginDestination = screen))
            return InterceptedGoToResult.Rewrite(AuthScreen(bounceBackScreen = screen))
        }
        return NavigationInterceptor.Skipped
    }
}
