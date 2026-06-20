// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
    alias(libs.plugins.roborazzi)
    alias(libs.plugins.kmp.parcel)
    alias(libs.plugins.burst)
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
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.tooling.preview)
        }

        commonTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.circuit.test)
            implementation(libs.dejavu)
            implementation(libs.kotlin.test)
        }
    }
}
