// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.lib)
    alias(libs.plugins.metro)
}

android { namespace = "dev.whosnickdoglio.spot.concurrency" }

// TODO investigate where the 17 could be coming from
// Inconsistent JVM targets between Java and Kotlin compile tasks: 17 and 21.
// To fix this issue, use the same JVM target for both tasks.
// For more details, see https://issuetracker.google.com/408242956.

dependencies {
    api(project(":concurrency:public"))
    api(libs.coroutines.android)
}
