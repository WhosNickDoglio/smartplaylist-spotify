// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android

import android.os.StrictMode
import androidx.compose.runtime.Composer
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import dev.whosnickdoglio.spot.BuildConfig
import dev.whosnickdoglio.spot.android.di.AndroidDependencyGraph
import dev.whosnickdoglio.spot.android.di.GraphOwner
import dev.zacsweers.metro.createGraph

internal class SpotApplication : GraphOwner() {
    override val graph: AndroidDependencyGraph by lazy {
        createGraph()
    }

    override fun onCreate() {
        super.onCreate()
        Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.Auto)
        if (BuildConfig.DEBUG) {
            StrictMode.setVmPolicy(StrictMode.VmPolicy.Builder().detectAll().penaltyLog().build())
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll().penaltyLog().build()
            )
        }
    }
}
