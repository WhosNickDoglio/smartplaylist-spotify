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
        includeGroup("com.google.testing.platform")
      }
    }
    mavenCentral()
  }
}

plugins {
  id("com.gradle.develocity") version "4.3.2"
  id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
  id("com.gradle.common-custom-user-data-gradle-plugin") version "2.4.0"
}

develocity {
  buildScan {
    termsOfUseUrl = "https://gradle.com/terms-of-service"
    termsOfUseAgree = "yes"
  }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

include(
    ":frontends:android",
    ":frontends:cli",
    ":frontends:desktop",
    ":frontends:web",
    ":commands:annotations",
    ":commands:auth",
    ":commands:clikt",
    ":commands:config",
    ":commands:create",
    ":kover-aggregate",
    ":spotify-rest",
)
