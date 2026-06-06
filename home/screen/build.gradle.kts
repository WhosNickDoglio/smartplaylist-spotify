// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.roborazzi)
}

dependencies {
    implementation(libs.circuit.foundation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(projects.auth.core)
    implementation(projects.auth.screen)
    implementation(projects.creation.screen)
    implementation(projects.playlists.screen)

    testImplementation(libs.assertk)
    testImplementation(libs.circuit.test)
    testImplementation(libs.dejavu)
    testImplementation(libs.junit)
}
