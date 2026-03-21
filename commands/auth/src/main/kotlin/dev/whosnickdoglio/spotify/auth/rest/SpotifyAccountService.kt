// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spotify.auth.rest

import com.slack.eithernet.ApiResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

public interface SpotifyAccountService {

    @POST("api/token")
    @FormUrlEncoded
    public suspend fun requestAccessToken(
        @Field("grant_type") grant: String = "client_credentials",
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
    ): ApiResult<AccessTokenResponse, Unit>

    @Suppress("LongParameterList")
    @GET("authorize")
    public suspend fun requestAuthorization(
        @Query("client_id") clientId: String,
        @Query("response_type") responseType: String = "code",
        @Query("redirect_uri") redirectUri: String,
        @Query("scope") scope: String,
        @Query("state") state: String,
        @Query("show_dialog") showDialog: Boolean = false,
    ): ApiResult<AuthorizationResponse, Unit>
}

@Serializable
public data class AccessTokenResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String,
    @SerialName("expires_in") val expiresIn: Int,
)

@Serializable public data class AuthorizationResponse(val code: String, val state: String)
