// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.kmp.parcel)
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.url"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.circuit.foundation)
            implementation(libs.circuitx.nav)
            implementation(libs.uri.kmp)
        }
    }
}
