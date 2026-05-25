// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
}

kotlin {
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.ktor.auth)
            implementation(libs.ktor.core)
            implementation(libs.ktor.contentNegotiation)
            implementation(libs.ktor.serialization)
            implementation(libs.ktor.cio)
            implementation(libs.ktor.logging)
            implementation(libs.kotlin.serialization)
        }
    }
}
