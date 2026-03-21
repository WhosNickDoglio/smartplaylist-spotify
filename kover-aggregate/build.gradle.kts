// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins { alias(libs.plugins.kover) }

kover { reports { filters { excludes { classes("*\$Metro*") } } } }

/*
This module just exists so Kover can create a merged report for code coverage.
 */
dependencies {
    allprojects.forEach { proj -> kover(proj) }
}
