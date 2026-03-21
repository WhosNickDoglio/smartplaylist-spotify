// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    `java-test-fixtures`
}

dependencies {
    implementation(libs.clikt)
    implementation(libs.mosiac.runtime)

    testFixturesImplementation(libs.clikt)
    testFixturesImplementation(libs.mosiac.runtime)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
