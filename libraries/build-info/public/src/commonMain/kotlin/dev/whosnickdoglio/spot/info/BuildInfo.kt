// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.info

public interface BuildInfo {
    public val versionName: String
    public val buildVariant: BuildVariant
}

public enum class BuildVariant {
    RELEASE,
    DEBUG,
}
