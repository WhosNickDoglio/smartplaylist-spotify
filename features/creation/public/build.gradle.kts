// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kmp.parcel)
    alias(libs.plugins.metro)
    alias(libs.plugins.app.platform)
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.creation"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.circuit.foundation)
            implementation(libs.circuit.serialization)
        }
    }
}
