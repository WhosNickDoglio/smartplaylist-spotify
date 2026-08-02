// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android.di

import dev.whosnickdoglio.spot.BuildConfig
import dev.whosnickdoglio.spot.info.BuildInfo
import dev.whosnickdoglio.spot.info.BuildVariant
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class AndroidBuildInfo : BuildInfo {
    override val versionName: String = BuildConfig.VERSION_NAME
    override val buildVariant: BuildVariant
        get() = if (BuildConfig.DEBUG) BuildVariant.DEBUG else BuildVariant.RELEASE
}
