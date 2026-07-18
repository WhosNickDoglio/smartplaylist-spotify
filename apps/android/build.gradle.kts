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
    implementation(project(":features:auth:core"))
    implementation(project(":features:auth:screen"))
    implementation(project(":features:creation:screen"))
    implementation(project(":features:playlists:screen"))
    implementation(project(":features:settings:screen"))
    implementation(project(":libraries:circuit-providers"))
    implementation(project(":libraries:concurrency"))
    implementation(project(":libraries:design"))
    implementation(project(":libraries:spotify-db"))
    implementation(project(":libraries:spotify-rest"))
    implementation(project(":libraries:targets"))
    implementation(project(":libraries:url-launcher"))
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
    implementation(libs.circuit.serialization)
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
