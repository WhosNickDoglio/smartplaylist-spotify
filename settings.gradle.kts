// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

rootProject.name = "smartplaylist-spotify"

pluginManagement {
  repositories {
    exclusiveContent {
      forRepository { google() }
      filter {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroup("com.google.testing.platform")
      }
    }
    exclusiveContent {
      forRepository { maven("https://storage.googleapis.com/r8-releases/raw") }
      filter { includeModule("com.android.tools", "r8") }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    exclusiveContent {
      forRepository { maven("https://storage.googleapis.com/r8-releases/raw") }
      filter { includeModule("com.android.tools", "r8") }
    }
    exclusiveContent {
      forRepository { google() }
      filter {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeGroup("com.google.testing.platform")
      }
    }
    mavenCentral()
  }
}

plugins {
  id("com.gradle.develocity") version "4.4.3"
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.6.0"
  id("com.fueledbycaffeine.spotlight") version "1.6.12"
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
  }
}
