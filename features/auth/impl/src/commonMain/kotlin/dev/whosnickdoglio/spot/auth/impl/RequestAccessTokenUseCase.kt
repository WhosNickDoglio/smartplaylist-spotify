// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.auth.Tokens
import dev.whosnickdoglio.spot.auth.impl.di.CodeVerifier
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.rest.AccessTokenRequestResponse
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.usecase.UseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext

public interface RequestAccessTokenUseCase : UseCase<String, Result<Tokens, AccessTokenFailure>>

public sealed interface AccessTokenFailure {
    public data object Error : AccessTokenFailure
}

private fun AccessTokenRequestResponse.toTokens(): Tokens =
    Tokens(
        accessToken = this.accessToken,
        scope = this.scope,
        expiresIn = this.expiresIn,
        refreshToken = this.refreshToken,
    )

@ContributesBinding(AppScope::class)
internal class DefaultRequestAccessTokenUseCase(
    @CodeVerifier private val codeVerifier: String,
    private val spotifyAccountService: SpotifyAccountService,
    private val coroutineContextProvider: CoroutineContextProvider,
) : RequestAccessTokenUseCase {
    override suspend fun invoke(arg: String): Result<Tokens, AccessTokenFailure> =
        withContext(coroutineContextProvider.io) {
            when (
                val response =
                    spotifyAccountService.requestAccessToken(
                        code = arg,
                        codeVerifier = codeVerifier,
                    )
            ) {
                is ApiResult.Failure -> Err(AccessTokenFailure.Error)
                is ApiResult.Success<AccessTokenRequestResponse> ->
                    return@withContext Ok(response.value.toTokens())
            }
        }
}
