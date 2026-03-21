// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.auth.rest

import dev.whosnickdoglio.spotify.rest.di.ClientId
import dev.whosnickdoglio.spotify.rest.di.ClientSecret
import dev.zacsweers.metro.Inject
import kotlin.io.encoding.Base64
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

@Inject
public class SpotifyAuthServiceAuthenticator(
    @param:ClientId private val clientId: String,
    @param:ClientSecret private val clientSecret: String,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        val encoded = Base64.encode("${clientId}:${clientSecret}".toByteArray())
        return response.newBuilder().addHeader("Authorization", "Basic $encoded").build().request
    }
}
