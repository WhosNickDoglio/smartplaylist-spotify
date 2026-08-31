// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.rest.testing"
        compileSdk { version = release(37) }
    }
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:spotify-rest:public"))
            api(libs.androidx.datastore.tink)
            api(libs.eithernet)
            api(libs.kotlin.serialization)
        }
    }
}
