// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

dependencies {
    api(libs.eithernet)
    api(libs.kotlin.serialization)
    api(libs.okhttp)
    api(libs.retrofit)
    api(libs.retrofit.serialization)

    implementation(libs.okhttp.logging)

    testImplementation(libs.assertk)
    testImplementation(libs.junit)
}
