// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.url

import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.CircuitSerializable
import dev.zacsweers.metro.AppScope
import io.github.solcott.kmp.parcelize.Parcelize

@Parcelize
@CircuitSerializable(AppScope::class)
public data class OpenUrlDesktop(val url: String) : DesktopScreen

public interface DesktopScreen : Screen
