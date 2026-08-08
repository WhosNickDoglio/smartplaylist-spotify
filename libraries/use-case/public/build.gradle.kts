// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    jvm()

    sourceSets { commonMain.dependencies { api(libs.coroutines.core) } }
}
