// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.burst)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    compilerOptions {
        optIn.add("dev.zacsweers.metro.ExperimentalMetroCoroutinesApi")
    }
    android {
        namespace = "dev.whosnickdoglio.encrypted.serialization"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(project(":libraries:concurrency:public"))
            implementation(libs.androidx.datastore)
            implementation(libs.coroutines.core)
            implementation(libs.cryptography.core)
            implementation(libs.cryptography.provider)
        }

        commonTest.dependencies {
            implementation(project(":libraries:concurrency:testing"))
            implementation(libs.assertk)
            implementation(libs.coroutines.test)
            implementation(libs.kotlin.test)
        }
    }
}
