// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.compose)
    alias(libs.plugins.gr8)
}

compose.desktop { application.mainClass = "dev.whosnickdoglio.spot.Main.kt" }

// TODO set this up better for CI
buildConfig {
    buildConfigField("CLIENT_ID", providers.environmentVariable("SPOTIFY_CLIENT_ID").orElse(""))
    buildConfigField(
        "CLIENT_SECRET",
        providers.environmentVariable("SPOTIFY_CLIENT_SECRET").orElse(""),
    )
    buildConfigField("VERSION", providers.provider { version.toString() }.orElse(""))
    packageName("dev.whosnickdoglio.spotify")
    useKotlinOutput { topLevelConstants = true }
}

dependencies {
    implementation(compose.desktop.currentOs)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.backhandler)
    implementation(projects.auth.screen)
    implementation(projects.circuitProviders)
    implementation(projects.spotifyRest)
}
