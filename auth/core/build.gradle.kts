// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.coroutines.core)
            implementation(libs.cryptography.core)
            implementation(libs.cryptography.provider)
            implementation(projects.spotifyRest)
        }
        commonTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.circuit.test)
            implementation(libs.dejavu)
            implementation(libs.kotlin.test)
        }
    }
}
