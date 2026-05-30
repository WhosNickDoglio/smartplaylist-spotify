// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

dependencies {
    implementation(libs.eithernet.retrofit)
    implementation(libs.kotlin.serialization)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    testImplementation(libs.assertk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.eithernet.test)
    testImplementation(libs.junit)
    testImplementation(libs.okhttp.mock)
    testImplementation(libs.retrofit.mock)
}
