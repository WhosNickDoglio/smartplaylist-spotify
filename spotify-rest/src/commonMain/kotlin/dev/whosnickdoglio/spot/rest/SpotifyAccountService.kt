// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

import com.slack.eithernet.ApiResult
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// TODO error handling
public interface SpotifyAccountService {

    // https://accounts.spotify.com/authorize
    // @GET("authorize")
    public suspend fun authorize(
        // @Query("client_id") clientId: String,
        // @Query("response_type") responseType: String = "code",
        // @Query("redirect_uri")
        // redirectUri: String = "https://whosnickdoglio.com/smartplaylist/callback",
        // @Query("state") state: String,
        // @Query("scope") scope: String,
        // @Query("code_challenge_method") codeChallengeMethod: String = "S256",
        // @Query("code_challenge") codeChallenge: String,
    ): ApiResult<AuthorizeResponse, Unit>

    // https://accounts.spotify.com/api/token
    // @POST("api/token")
    // @FormUrlEncoded
    public suspend fun requestAccessToken(
        // @Field("grant_type") grantType: String = "authorization_code",
        // @Field("code") code: String,
        // @Field("redirect_uri") redirectUri: String,
        // @Field("client_id") clientID: String,
        // @Field("code_verifier") codeVerifier: String,
    ): ApiResult<AccessTokenRequestResponse, Unit>
}

@Serializable public data class AuthorizeResponse(val code: String, val state: String)

@Serializable
public data class AccessTokenRequestResponse(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String = "Bearer",
    @SerialName("scope") val scope: String,
    @SerialName("expires_in") val expiresIn: Int,
    @SerialName("refresh_token") val refreshToken: String,
)
