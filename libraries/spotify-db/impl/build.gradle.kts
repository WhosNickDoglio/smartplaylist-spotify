// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

plugins {
    alias(libs.plugins.convention.kmp)
    alias(libs.plugins.android.library.kmp)
    alias(libs.plugins.metro)
    alias(libs.plugins.burst)
    alias(libs.plugins.sqldelight)
    alias(libs.plugins.app.platform)
}

appPlatform {
    enableModuleStructure(true)
}

sqldelight {
    databases {
        register("SpotDb") {
            packageName.set("dev.whosnickdoglio.spot.db.impl")
            // generateAsync = true
        }
    }
}

kotlin {
    android {
        namespace = "dev.whosnickdoglio.spot.db.impl"
        compileSdk { version = release(37) }
    }
    jvm()

    sourceSets {
        androidMain.dependencies { implementation(libs.sqldelight.android) }

        jvmMain.dependencies { implementation(libs.sqldelight.jvm) }

        commonMain.dependencies {
            api(project(":libraries:spotify-db:public"))

            implementation(project(":libraries:concurrency:public"))
            implementation(libs.datetime)
            implementation(libs.sqldelight.coroutines)
        }

        commonTest.dependencies {
            implementation(project(":libraries:concurrency:testing"))
            implementation(libs.assertk)
            implementation(libs.kotlin.test)
        }
    }
}
