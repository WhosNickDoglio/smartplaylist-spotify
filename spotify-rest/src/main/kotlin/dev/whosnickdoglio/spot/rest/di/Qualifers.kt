// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.di

import dev.zacsweers.metro.Qualifier

@Qualifier public annotation class UnauthorizedClient

@Qualifier public annotation class AuthorizedClient

@Qualifier internal annotation class ClientSecret

@Qualifier internal annotation class ClientId
