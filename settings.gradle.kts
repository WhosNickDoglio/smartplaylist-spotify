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
        includeModule("com.android.tools", "r8")
        includeGroup("com.google.testing.platform")
      }
    }
    mavenCentral()
    gradlePluginPortal()
  }
}

dependencyResolutionManagement {
  repositories {
    exclusiveContent {
      forRepository { google() }
      filter {
        includeGroupAndSubgroups("androidx")
        includeGroupAndSubgroups("com.android")
        includeModule("com.android.tools", "r8")
        includeGroup("com.google.testing.platform")
      }
    }
    mavenCentral()
  }
}

plugins {
  id("com.gradle.develocity") version "4.5.0"
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.7.0"
  id("com.fueledbycaffeine.spotlight") version "1.6.12"
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
  }
}
