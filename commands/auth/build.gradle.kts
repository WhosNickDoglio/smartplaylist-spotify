// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.mockingbird)
}

dependencies {
    api(libs.eithernet)
    api(libs.kotlin.serialization)
    api(libs.okhttp)
    api(libs.retrofit)
    api(libs.retrofit.serialization)
    api(projects.spotifyRest)

    implementation(libs.clikt)
    implementation(libs.okhttp.logging)
    implementation(projects.commands.annotations)
    implementation(projects.commands.clikt)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
    testImplementation(libs.retrofit.mock)
}
