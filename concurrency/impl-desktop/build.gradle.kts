// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
plugins {
    alias(libs.plugins.convention.kotlin)
    alias(libs.plugins.metro)
}

dependencies {
    api(libs.coroutines.android)
    api(projects.concurrency.public)
}
