/*
 * Copyright (C) 2025 Nicholas Doglio
 * SPDX-License-Identifier: MIT
 */

buildscript { dependencies { classpath(libs.burst) } }

plugins {
    alias(libs.plugins.dependencyAnalysis)
    alias(libs.plugins.doctor)
    alias(libs.plugins.android.app) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.detekt) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.lint) apply false
    alias(libs.plugins.sortDependencies) apply false
    alias(libs.plugins.spotless) apply false
}

doctor {
    // https://github.com/runningcode/gradle-doctor/pull/419
    @Suppress("DEPRECATION")
    warnWhenNotUsingParallelGC = false
}

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure {
    languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
    vendor = JvmVendorSpec.AZUL
}
