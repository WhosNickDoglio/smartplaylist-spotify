// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
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
    packageName("dev.whosnickdoglio.spotify")
    useKotlinOutput { topLevelConstants = true }
}

kotlin {
    jvm()
    sourceSets {
        jvmMain.dependencies {
            api(libs.eithernet.retrofit)
            implementation(libs.kotlin.serialization)
            implementation(libs.okhttp)
            implementation(libs.okhttp.logging)
            implementation(libs.retrofit)
            implementation(libs.retrofit.converter)
        }

        jvmTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.coroutines.test)
            implementation(libs.eithernet.test)
            implementation(libs.junit)
            implementation(libs.okhttp.mock)
            implementation(libs.retrofit.mock)
        }
    }
}
