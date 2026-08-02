// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.di

import dev.whosnickdoglio.spot.info.BuildInfo
import dev.whosnickdoglio.spot.info.BuildVariant
import dev.whosnickdoglio.spotify.VERSION
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class DesktopBuildInfo : BuildInfo {
    override val versionName: String = VERSION
    // TODO how do I handle this in desktop app
    override val buildVariant: BuildVariant = BuildVariant.RELEASE
}
