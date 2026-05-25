// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.buildConfig)
}

kotlin {
    js { browser() }

    // https://youtrack.jetbrains.com/issue/KT-84582/coreLibrariesVersion-isnt-really-compatible-with-JS-or-Wasm
    //    wasmJs { browser() }

    applyDefaultHierarchyTemplate()
}
