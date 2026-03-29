// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.mockingbird)
}

dependencies {
    api(projects.spotifyRest)

    implementation(libs.clikt)
    implementation(libs.coroutines.core)
    implementation(projects.commands.annotations)
    implementation(projects.commands.clikt)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
