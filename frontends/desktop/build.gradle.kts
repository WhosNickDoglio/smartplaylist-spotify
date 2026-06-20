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
    alias(libs.plugins.licensee)
}

licensee {
    allow("Apache-2.0")
    // Pulled in by SLF4J (transitive dependency of ktor)
    allowUrl("https://opensource.org/license/mit")
}

gr8 {
    val shadowedJar =
        create("gr8") {
            // program jars are included in the final shadowed jar
            addProgramJarsFrom(configurations.getByName("runtimeClasspath"))
            addProgramJarsFrom(tasks.getByName("jar"))
            proguardFile("rules.pro")

            // Use a version from https://storage.googleapis.com/r8-releases/raw
            // Requires a maven("https://storage.googleapis.com/r8-releases/raw") repository
            r8Version("8.8.19")
            // Or use a commit
            // The jar is downloaded on demand
            r8Version("887704078a06fc0090e7772c921a30602bf1a49f")
            // Or leave it to the default version
        }
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
    implementation(project(":auth:core"))
    implementation(project(":auth:screen"))
    implementation(project(":creation:screen"))
    implementation(project(":libraries:circuit-providers"))
    implementation(project(":libraries:concurrency:impl-desktop"))
    implementation(project(":libraries:design"))
    implementation(project(":libraries:spotify-rest"))
    implementation(project(":libraries:targets"))
    implementation(project(":playlists:screen"))
    implementation(project(":settings:screen"))
    implementation(compose.desktop.currentOs)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(libs.circuitx.nav)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.backhandler)
}
