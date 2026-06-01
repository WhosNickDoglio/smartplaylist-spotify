// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.di

import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.rest.SpotifyService
import dev.whosnickdoglio.spotify.CLIENT_ID
import dev.whosnickdoglio.spotify.CLIENT_SECRET
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.json.Json
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create

@ContributesTo(AppScope::class)
public interface NetworkProviders {

    @Provides @ClientSecret public fun provideClientSecret(): String = CLIENT_SECRET

    @Provides @ClientId public fun provideClientId(): String = CLIENT_ID

    @SingleIn(AppScope::class)
    @UnauthorizedClient
    @Provides
    public fun provideUnauthorizedHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = Level.BODY })
            .build()

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

    @SingleIn(AppScope::class)
    @Provides
    public fun provideSpotifyAccountService(
        @UnauthorizedClient client: Lazy<OkHttpClient>
    ): SpotifyAccountService =
        Retrofit.Builder()
            .baseUrl("https://accounts.spotify.com/")
            .callFactory(client.value::newCall)
            .addConverterFactory(ApiResultConverterFactory)
            .addConverterFactory(
                Json.asConverterFactory("application/json; charset=utf-8".toMediaType())
            )
            .addCallAdapterFactory(ApiResultCallAdapterFactory)
            .build()
            .create()
}
