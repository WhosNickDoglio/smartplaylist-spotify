// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

dependencies {
    implementation(libs.clikt)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
