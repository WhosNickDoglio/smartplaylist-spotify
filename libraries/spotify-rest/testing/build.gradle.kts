// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(project(":libraries:spotify-rest:public"))
            api(libs.eithernet)

            implementation(project(":libraries:eithernet-ktor:public"))
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.okio)
        }

        commonTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.coroutines.test)
            implementation(libs.eithernet.test)
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
        }
    }
}
