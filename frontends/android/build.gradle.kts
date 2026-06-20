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
    implementation(project(":auth:core"))
    implementation(project(":auth:screen"))
    implementation(project(":circuit-providers"))
    implementation(project(":concurrency:impl-android"))
    implementation(project(":creation:screen"))
    implementation(project(":design"))
    implementation(project(":playlists:screen"))
    implementation(project(":settings:screen"))
    implementation(project(":spotify-db"))
    implementation(project(":spotify-rest"))
    implementation(project(":targets"))
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
    implementation(libs.circuitx.nav)
    implementation(libs.metrox.android)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.leakcanary)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.workmanager.test)

    coreLibraryDesugaring(libs.desugar)
}
