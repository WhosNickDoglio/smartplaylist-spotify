// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins { alias(libs.plugins.convention.kmp) }

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.eithernet)
            implementation(libs.ktor.client.core)
            implementation(libs.okio)
        }

        commonTest.dependencies { implementation(libs.kotlin.test) }
    }
}
