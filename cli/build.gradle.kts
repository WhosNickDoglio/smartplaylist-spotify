// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.buildConfig)
    application
}

application {
    mainClass = "dev.whosnickdoglio.spotify.cli.MainKt"
    applicationName = "sps"
}

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

group = "dev.whosnickdoglio"

version = "1.0-SNAPSHOT"

dependencies {
    implementation(libs.clikt)
    implementation(projects.commands.annotations)
    implementation(projects.commands.auth)
    implementation(projects.commands.config)
    implementation(projects.commands.create)
    implementation(projects.spotifyRest)
}
