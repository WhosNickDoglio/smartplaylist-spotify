// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.metro)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.circuit.foundation)
            api(libs.circuitx.sub)
        }
    }
}
