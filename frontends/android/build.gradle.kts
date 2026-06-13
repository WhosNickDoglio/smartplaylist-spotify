// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.app)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.parcel)
    alias(libs.plugins.metro)
    alias(libs.plugins.licensee)
    alias(libs.plugins.app.versioning)
}

licensee {
    allow("Apache-2.0")
    // Pulled in by SLF4J (transitive dependency of ktor)
    allowUrl("https://opensource.org/license/mit")
    bundleAndroidAsset = true
}

android {
    namespace = "dev.whosnickdoglio.spot"
    defaultConfig {
        applicationId = "dev.whosnickdoglio.spot"
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures { buildConfig = true }
    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    lint {
        disable.add(
            // Using MetroX Android which provides a custom AppComponentFactory
            "Instantiatable"
        )
    }
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.workmanager)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(libs.circuitx.android)
    implementation(libs.circuitx.gesture)
    implementation(libs.metrox.android)
    implementation(projects.auth.core)
    implementation(projects.auth.screen)
    implementation(projects.circuitProviders)
    implementation(projects.concurrency.implAndroid)
    implementation(projects.creation.screen)
    implementation(projects.design)
    implementation(projects.home.screen)
    implementation(projects.playlists.screen)
    implementation(projects.settings.screen)
    implementation(projects.spotifyDb)
    implementation(projects.spotifyRest)
    implementation(projects.targets)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.leakcanary)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.workmanager.test)

    coreLibraryDesugaring(libs.desugar)
}
