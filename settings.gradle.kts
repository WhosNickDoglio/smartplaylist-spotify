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
    ":auth:core",
    ":auth:screen",
    ":circuit-providers",
    ":creation:screen",
    ":frontends:android",
    ":frontends:desktop",
    ":home:screen",
    ":kover-aggregate",
    ":playlists:screen",
    ":spotify-db",
    ":spotify-rest",
    ":targets",
)

include(":concurrency:impl-android")

include(":concurrency:public")

include(":concurrency:impl-desktop")
