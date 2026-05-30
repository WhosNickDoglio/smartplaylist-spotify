// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface NetworkProviders {

    @SingleIn(AppScope::class)
    @UnauthorizedClient
    @Provides
    public fun provideUnauthorizedHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @SingleIn(AppScope::class)
    @AuthorizedClient
    @Provides
    public fun provideAuthorizedHttpClient(
        spotifyAuthenticator: Authenticator,
        authInterceptor: Interceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .authenticator(spotifyAuthenticator)
            .addInterceptor(authInterceptor)
            .build()

    @SingleIn(AppScope::class)
    @Provides
    public fun provideSpotifyService(@AuthorizedClient client: Lazy<OkHttpClient>): SpotifyService =
        Retrofit.Builder()
            .baseUrl("")
            .callFactory(client.value::newCall)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=utf-8".toMediaType())
            )
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .build()
            .create()
}

// Authorized
@Qualifier public annotation class UnauthorizedClient

@Qualifier public annotation class AuthorizedClient
