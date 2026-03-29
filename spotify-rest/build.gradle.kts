// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

dependencies {
    api(libs.spotify.api)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
