// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.auth.rest

import dev.zacsweers.metro.Inject
import okhttp3.Interceptor
import okhttp3.Response

@Inject
public class SpotifyAuthHeaderInterceptor : Interceptor {
    private val token: String = TODO()

    override fun intercept(chain: Interceptor.Chain): Response {
        val updatedRequest =
            chain.request().newBuilder().addHeader("Authorization", "Bearer $token").build()

        return chain.proceed(updatedRequest)
    }
}
