// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.sqldelight)
}

sqldelight { databases { register("SpotDb") { packageName.set("dev.whosnickdoglio.spot") } } }

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.db"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        androidMain.dependencies { implementation(libs.sqldelight.android) }

        jvmMain.dependencies { implementation(libs.sqldelight.jvm) }

        commonMain.dependencies {
            implementation(libs.sqldelight.coroutines)
            // implementation(libs.sqldelight.native)
        }

        commonTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.circuit.test)
            implementation(libs.dejavu)
            implementation(libs.kotlin.test)
        }
    }
}
