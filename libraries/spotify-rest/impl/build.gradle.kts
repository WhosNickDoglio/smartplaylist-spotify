// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.buildConfig)
    alias(libs.plugins.app.platform)
    alias(libs.plugins.burst)
    alias(libs.plugins.mockingbird)
}

appPlatform {
    enableModuleStructure(true)
}

// TODO set this up better for CI
buildConfig {
    buildConfigField("CLIENT_ID", providers.environmentVariable("SPOTIFY_CLIENT_ID").orElse(""))
    packageName("dev.whosnickdoglio.spot.rest.impl")
    useKotlinOutput { topLevelConstants = true }
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.rest.impl"
        compileSdk { version = release(37) }
    }
    jvm()
    sourceSets {
        androidMain.dependencies {
            implementation(libs.slf4j.android)
        }

        commonMain.dependencies {
            api(project(":libraries:spotify-rest:public"))
            api(libs.androidx.datastore.tink)
            api(libs.eithernet)

            implementation(project(":libraries:build-info:public"))
            implementation(project(":libraries:concurrency:public"))
            implementation(project(":libraries:eithernet-ktor:public"))
            implementation(libs.kotlin.serialization)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.cio)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.livewire.ktor)
            implementation(libs.okio)
        }

        commonTest.dependencies {
            implementation(libs.assertk)
            implementation(libs.coroutines.test)
            implementation(libs.eithernet.test)
            implementation(libs.kotlin.test)
            implementation(libs.ktor.client.mock)
        }
    }
}
