// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import dev.whosnickdoglio.spot.android.di.injector
import dev.whosnickdoglio.spot.android.theme.SmartplaylistspotifyTheme
import dev.whosnickdoglio.spot.auth.AuthScreen

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartplaylistspotifyTheme {
                // TODO Parcelable nonsense
                CircuitCompositionLocals(injector().circuit) { CircuitContent(AuthScreen) }
            }
        }
    }
}
