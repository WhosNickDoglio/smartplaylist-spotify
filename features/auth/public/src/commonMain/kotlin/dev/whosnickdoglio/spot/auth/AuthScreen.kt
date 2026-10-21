// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

import catchup.deeplink.DeepLinkable
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.StringKey

@CircuitSerializable(AppScope::class)
public data class AuthScreen(
    val code: String? = null,
    val state: String? = null,
) : Screen {
    @StringKey("auth")
    @ContributesIntoMap(AppScope::class)
    public object DeepLinker : DeepLinkable {
        override fun createScreen(queryParams: Map<String, List<String?>>): Screen =
            AuthScreen(
                code = queryParams["code"]?.first(),
                state = queryParams["state"]?.first(),
            )
    }
}
