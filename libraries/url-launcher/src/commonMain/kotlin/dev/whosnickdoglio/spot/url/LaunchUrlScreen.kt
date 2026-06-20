// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import com.slack.circuit.runtime.screen.Screen
import io.github.solcott.kmp.parcelize.Parcelize

@Parcelize public data class LaunchUrlScreen(val url: String) : Screen
