// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okhttp3.Interceptor
import okhttp3.Response

@ContributesBinding(AppScope::class)
internal class SpotifyAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        TODO("Not yet implemented")
    }
}
