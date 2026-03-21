// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.clikt.compose

import androidx.compose.runtime.Composable
import com.github.ajalt.clikt.testing.CliktCommandTestResult
import com.github.ajalt.clikt.testing.test
import com.github.ajalt.mordant.input.InputEvent
import com.github.ajalt.mordant.rendering.AnsiLevel

@Composable
fun ComposableCliktCommand.test(
    argv: Array<String>,
    stdin: String = "",
    envvars: Map<String, String> = emptyMap(),
    includeSystemEnvvars: Boolean = false,
    ansiLevel: AnsiLevel = AnsiLevel.NONE,
    width: Int = 79,
    height: Int = 24,
    hyperlinks: Boolean = ansiLevel != AnsiLevel.NONE,
    outputInteractive: Boolean = ansiLevel != AnsiLevel.NONE,
    inputInteractive: Boolean = ansiLevel != AnsiLevel.NONE,
    inputEvents: List<InputEvent> = emptyList(),
): CliktCommandTestResult {
    return test(
        argv.asList(), stdin, envvars, includeSystemEnvvars, ansiLevel, width, height,
        hyperlinks, outputInteractive, inputInteractive, inputEvents
    )
}


@Composable
fun ComposableCliktCommand.test(
    argv: List<String>,
    stdin: String = "",
    envvars: Map<String, String> = emptyMap(),
    includeSystemEnvvars: Boolean = false,
    ansiLevel: AnsiLevel = AnsiLevel.NONE,
    width: Int = 79,
    height: Int = 24,
    hyperlinks: Boolean = ansiLevel != AnsiLevel.NONE,
    outputInteractive: Boolean = ansiLevel != AnsiLevel.NONE,
    inputInteractive: Boolean = ansiLevel != AnsiLevel.NONE,
    inputEvents: List<InputEvent> = emptyList(),
): CliktCommandTestResult {
    return test(
        argv, stdin, envvars, includeSystemEnvvars, ansiLevel, width, height,
        hyperlinks, outputInteractive, inputInteractive, inputEvents
    ) {
//        parse(it)
    }
}
