// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.app)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcel)
    alias(libs.plugins.metro)
}

android {
    namespace = "dev.whosnickdoglio.spot"
    defaultConfig {
        applicationId = "dev.whosnickdoglio.spot"
        versionCode = 1
        versionName = "1.0"
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.workmanager)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(projects.auth.screen)
    implementation(projects.circuitProviders)
    implementation(projects.concurrency.implAndroid)
    implementation(projects.design)
    implementation(projects.targets)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.workmanager.test)

    coreLibraryDesugaring(libs.desugar)
}
