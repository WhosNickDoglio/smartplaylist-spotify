// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.livewire"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            api(libs.livewire.client)

            implementation(libs.livewire.compose)
            implementation(libs.livewire.network)
            implementation(libs.livewire.preferences)
            implementation(libs.livewire.sql)
        }
    }
}
