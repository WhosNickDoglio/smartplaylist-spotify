// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.rest.di

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.whosnickdoglio.spotify.auth.rest.SpotifyAccountService
import dev.whosnickdoglio.spotify.auth.rest.SpotifyAuthServiceAuthenticator
import dev.whosnickdoglio.spotify.auth.rest.SpotifyAuthServiceInterceptor
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface AuthContributor {

    @Provides
    @AuthOkhttp
    public fun provideOkhttp(
        authenticator: SpotifyAuthServiceAuthenticator,
        authServiceInterceptor: SpotifyAuthServiceInterceptor,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .authenticator(authenticator)
            .addInterceptor(authServiceInterceptor)
            .addNetworkInterceptor(
                HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            )
            .build()

    @Provides
    public fun providesSpotifyAccountService(
        @AuthOkhttp client: Lazy<OkHttpClient>
    ): SpotifyAccountService =
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

@Qualifier internal annotation class AuthOkhttp
