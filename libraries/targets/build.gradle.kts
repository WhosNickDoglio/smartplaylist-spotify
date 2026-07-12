// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.targets"
        compileSdk { version = release(37) }
    }
    jvm()
}
