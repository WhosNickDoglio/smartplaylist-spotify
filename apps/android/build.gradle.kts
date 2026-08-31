// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.app)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.metro)
    alias(libs.plugins.licensee)
    alias(libs.plugins.app.versioning)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add(
            // TODO ignoring for now to work around
            "-Xwarning-level=SUSPICIOUS_UNUSED_MULTIBINDING:disabled"
        )
    }
}

licensee {
    allow("Apache-2.0")
    allow("MIT")
    // com.michael-bull.kotlin-result:kotlin-result
    allow("ISC")
    // androidx.datastore:datastore-preferences-external-protobuf
    allow("BSD-3-Clause")
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
    implementation(project(":features:auth:impl"))
    implementation(project(":features:creation:impl"))
    implementation(project(":features:playlists:impl"))
    implementation(project(":features:settings:impl"))
    implementation(project(":libraries:build-info:public"))
    implementation(project(":libraries:circuit-providers:impl"))
    implementation(project(":libraries:concurrency:impl"))
    implementation(project(":libraries:deeplink:impl"))
    implementation(project(":libraries:design:public"))
    implementation(project(":libraries:livewire:impl"))
    implementation(project(":libraries:spotify-db:public"))
    implementation(project(":libraries:spotify-rest:impl"))
    implementation(project(":libraries:targets:public"))
    implementation(project(":libraries:url-launcher:impl"))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.browser)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.startup)
    implementation(libs.androidx.workmanager)
    implementation(libs.circuit.codegen.annotations)
    implementation(libs.circuit.foundation)
    implementation(libs.circuit.serialization)
    implementation(libs.circuitx.android)
    implementation(libs.circuitx.gesture)
    implementation(libs.circuitx.nav)
    implementation(libs.metrox.android)
    implementation(libs.slf4j.android)

    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.leakcanary)

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.workmanager.test)

    coreLibraryDesugaring(libs.desugar)
}
