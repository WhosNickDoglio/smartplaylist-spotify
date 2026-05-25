// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose)
}

kotlin {
    js { browser() }

    // https://youtrack.jetbrains.com/issue/KT-84582/coreLibrariesVersion-isnt-really-compatible-with-JS-or-Wasm
    //    wasmJs { browser() }

    applyDefaultHierarchyTemplate()

    sourceSets {
        webMain.dependencies {
            implementation(projects.circuitProviders)
            implementation(libs.circuit.codegen.annotations)
            implementation(libs.circuit.foundation)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.runtime)
            implementation(libs.compose.ui)
            implementation(libs.compose.ui.backhandler)
        }
    }
}
