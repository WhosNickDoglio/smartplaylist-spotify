// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.targets

public sealed interface Target {
    public data object Android : Target

    public data object Desktop : Target
}
