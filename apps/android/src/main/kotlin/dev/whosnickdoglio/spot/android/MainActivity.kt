// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import catchup.deeplink.DeepLinkHandler
import catchup.deeplink.parse
import com.slack.circuit.retained.ExperimentalCircuitRetainedApi
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
internal class MainActivity(
    private val appFactory: App.Factory,
    private val deepLinkHandler: DeepLinkHandler,
) : ComponentActivity() {

    @OptIn(ExperimentalCircuitRetainedApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        val backStack = deepLinkHandler.parse(intent) ?: listOf(PlaylistScreen)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = appFactory.create(this, backStack)
        setContent {
            app()
        }
    }
}
