// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.cryptography.core)
    implementation(libs.cryptography.provider)
    implementation(projects.spotifyRest)
}
