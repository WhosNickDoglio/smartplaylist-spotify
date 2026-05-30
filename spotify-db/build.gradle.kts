// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.sqldelight)
}

sqldelight { databases { register("SpotDb") { packageName.set("dev.whosnickdoglio.spot") } } }

dependencies {
    implementation(libs.sqldelight.coroutines)
    implementation(libs.sqldelight.jvm)

    testImplementation(libs.assertk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.eithernet.test)
    testImplementation(libs.junit)
    testImplementation(libs.okhttp.mock)
    testImplementation(libs.retrofit.mock)
}
