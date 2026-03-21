// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.auth.rest

import dev.whosnickdoglio.spotify.rest.di.ClientId
import dev.whosnickdoglio.spotify.rest.di.ClientSecret
import dev.zacsweers.metro.Inject
import kotlin.io.encoding.Base64
import okhttp3.Interceptor
import okhttp3.Response

@Inject
public class SpotifyAuthServiceInterceptor(
    @param:ClientId private val clientId: String,
    @param:ClientSecret private val clientSecret: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val encoded = Base64.encode("${clientId}:${clientSecret}".toByteArray())

        val updatedRequest =
            chain.request().newBuilder().addHeader("Authorization", "Basic $encoded").build()

        return chain.proceed(updatedRequest)
    }
}
