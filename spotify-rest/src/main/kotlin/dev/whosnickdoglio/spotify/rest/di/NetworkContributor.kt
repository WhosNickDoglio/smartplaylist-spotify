// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.rest.di

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.whosnickdoglio.spotify.rest.SpotifyService
import dev.whosnickdoglio.spotify.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spotify.rest.auth.SpotifyAuthHeaderInterceptor
import dev.whosnickdoglio.spotify.rest.auth.SpotifyAuthenticator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface NetworkContributor {

    @Provides
    public fun provideOkhttp(
        authenticator: SpotifyAuthenticator,
        interceptor: SpotifyAuthHeaderInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(interceptor)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    public fun providesSpotifyService(client: Lazy<OkHttpClient>): SpotifyService =
        Retrofit.Builder()
            .baseUrl("https://api.spotify.com/")
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .callFactory { client.value.newCall(it) }
            .build()
            .create()

    @Provides
    public fun providesSpotifyAccountService(client: Lazy<OkHttpClient>): SpotifyAccountService =
        Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .addConverterFactory(ApiResultConverterFactory)
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=UTF8".toMediaType())
            )
            .callFactory { client.value.newCall(it) }
            .build()
            .create()
}
