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
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add(
            // TODO figure out how to handle deeplinking in Desktop app
            "-Xwarning-level=SUSPICIOUS_UNUSED_MULTIBINDING:disabled"
        )
    }
}

licensee {
    allow("Apache-2.0")
    // androidx.datastore:datastore-preferences-external-protobuf
    allow("BSD-3-Clause")
    // Pulled in by SLF4J (transitive dependency of ktor)
    allowUrl("https://opensource.org/license/mit")
}

compose.desktop { application.mainClass = "dev.whosnickdoglio.spot.Main" }

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
    implementation(project(":features:auth:impl"))
    implementation(project(":features:creation:impl"))
    implementation(project(":features:playlists:impl"))
    implementation(project(":features:settings:impl"))
    implementation(project(":libraries:build-info:public"))
    implementation(project(":libraries:circuit-providers:impl"))
    implementation(project(":libraries:concurrency:public"))
    implementation(project(":libraries:deeplink:impl"))
    implementation(project(":libraries:design:public"))
    implementation(project(":libraries:livewire:impl"))
    implementation(project(":libraries:spotify-db:public"))
    implementation(project(":libraries:spotify-rest:public"))
    implementation(project(":libraries:targets:public"))
    implementation(project(":libraries:url-launcher:public"))
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
