// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitCompositionLocals
import com.slack.circuit.foundation.CircuitContent
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.screen.Screen
import dev.whosnickdoglio.spot.android.di.injector
import dev.whosnickdoglio.spot.android.theme.SmartplaylistspotifyTheme
import dev.zacsweers.metro.AppScope
import kotlinx.parcelize.Parcelize

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartplaylistspotifyTheme {
                CircuitCompositionLocals(injector().circuit) { CircuitContent(DummyScreen) }
            }
        }
    }
}

@Parcelize
internal data object DummyScreen : Screen {
    data class State(val hello: String) : CircuitUiState
}

@CircuitInject(DummyScreen::class, AppScope::class)
@Composable
internal fun DummyScreen(state: DummyScreen.State, modifier: Modifier = Modifier) {
    Column(modifier) { Text(state.hello) }
}

@SuppressLint("ComposableNaming")
@CircuitInject(DummyScreen::class, AppScope::class)
@Composable
internal fun DummyPresenter(): DummyScreen.State = DummyScreen.State("Hello world!")
