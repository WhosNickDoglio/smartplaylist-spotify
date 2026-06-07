// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.di

import dev.zacsweers.metro.Qualifier

@Qualifier public annotation class UnauthorizedClient

@Qualifier public annotation class AuthorizedClient

@Qualifier public annotation class ClientSecret

@Qualifier public annotation class ClientId
