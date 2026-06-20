// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    // alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
}

kotlin {
    // android {
    //     namespace = "dev.whosnickdoglio.spot.concurrency"
    //     compileSdk { version = release(37) }
    // }
    jvm()

    sourceSets { commonMain.dependencies { api(libs.coroutines.core) } }
}
