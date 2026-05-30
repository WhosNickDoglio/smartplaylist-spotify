// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

buildscript { dependencies { classpath(libs.burst) } }

plugins {
  alias(libs.plugins.dependencyAnalysis)
  alias(libs.plugins.doctor)
  alias(libs.plugins.ktfmt)
  alias(libs.plugins.android.app) apply false
  alias(libs.plugins.android.library) apply false
  alias(libs.plugins.cacheFix) apply false
  alias(libs.plugins.compose) apply false
  alias(libs.plugins.convention.app) apply false
  alias(libs.plugins.convention.lib) apply false
  alias(libs.plugins.convention.kotlin) apply false
  alias(libs.plugins.detekt) apply false
  alias(libs.plugins.gr8) apply false
  alias(libs.plugins.kotlin.compose) apply false
  alias(libs.plugins.kotlin.jvm) apply false
  alias(libs.plugins.kotlin.serialization) apply false
  alias(libs.plugins.kotlin.parcel) apply false
  alias(libs.plugins.kover) apply false
  alias(libs.plugins.lint) apply false
  alias(libs.plugins.sortDependencies) apply false
  alias(libs.plugins.metro) apply false
  alias(libs.plugins.buildConfig) apply false
  alias(libs.plugins.mockingbird) apply false
  alias(libs.plugins.sqldelight) apply false
}

dependencyAnalysis { useTypesafeProjectAccessors(true) }

// https://docs.gradle.org/8.9/userguide/gradle_daemon.html#daemon_jvm_criteria
tasks.updateDaemonJvm.configure {
  languageVersion = JavaLanguageVersion.of(libs.versions.jdk.get())
  vendor = JvmVendorSpec.AZUL
}
