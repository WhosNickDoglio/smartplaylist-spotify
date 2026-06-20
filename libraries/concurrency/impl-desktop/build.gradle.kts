// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
}

dependencies {
    api(project(":libraries:concurrency:public"))
    api(libs.coroutines.swing)
}
