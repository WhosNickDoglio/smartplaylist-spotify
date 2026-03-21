// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
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
}
