// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.metro)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.compose)
    // alias(libs.plugins.shadow)
    alias(libs.plugins.licensee)
}

licensee {
    allow("Apache-2.0")
    // Pulled in by SLF4J (transitive dependency of ktor)
    allowUrl("https://opensource.org/license/mit")
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

// tasks.shadowJar.configure {
//     minimize {
//         r8 {
//             keepRuleFiles.from(layout.projectDirectory.file("rules"))
//             enableOptimization()
//         }
//     }
// }

dependencies {
    implementation(project(":features:auth:core"))
    implementation(project(":features:auth:screen"))
    implementation(project(":features:creation:screen"))
    implementation(project(":features:playlists:screen"))
    implementation(project(":features:settings:screen"))
    implementation(project(":libraries:circuit-providers"))
    implementation(project(":libraries:concurrency"))
    implementation(project(":libraries:design"))
    implementation(project(":libraries:spotify-db"))
    implementation(project(":libraries:spotify-rest"))
    implementation(project(":libraries:targets"))
    implementation(project(":libraries:url-launcher"))
    implementation(compose.desktop.currentOs)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(libs.circuit.serialization)
    implementation(libs.circuitx.nav)
    implementation(libs.compose.foundation)
    implementation(libs.compose.material3)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.backhandler)
}
