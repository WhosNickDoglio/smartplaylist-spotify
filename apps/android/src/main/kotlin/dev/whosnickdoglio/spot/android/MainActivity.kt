// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.slack.circuit.retained.CircuitRetainedSettings
import com.slack.circuit.retained.ExperimentalCircuitRetainedApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.binding
import dev.zacsweers.metrox.android.ActivityKey

@ContributesIntoMap(AppScope::class, binding<Activity>())
@ActivityKey
internal class MainActivity(private val appFactory: App.Factory) : ComponentActivity() {

    @OptIn(ExperimentalCircuitRetainedApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        CircuitRetainedSettings.useFirstParty = true
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = appFactory.create(this)
        setContent {
            app()
        }
    }
}
