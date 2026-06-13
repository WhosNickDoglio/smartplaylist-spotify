// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildConfig)
}

// TODO set this up better for CI
buildConfig {
    buildConfigField("CLIENT_ID", providers.environmentVariable("SPOTIFY_CLIENT_ID").orElse(""))
    buildConfigField(
        "CLIENT_SECRET",
        providers.environmentVariable("SPOTIFY_CLIENT_SECRET").orElse(""),
    )
    packageName("dev.whosnickdoglio.spot.rest")
    useKotlinOutput { topLevelConstants = true }
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.rest"
        compileSdk { version = release(37) }
    }
    jvm()
    sourceSets {
        commonMain.dependencies {
            api(libs.eithernet)
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.okio)
            api(libs.uri.kmp)
            implementation(projects.eithernetKtor)
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
