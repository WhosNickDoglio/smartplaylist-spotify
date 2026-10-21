// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal.domain

import dev.whosnickdoglio.spot.auth.impl.di.State
import dev.whosnickdoglio.spot.auth.impl.internal.CodeChallenge
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.usecase.NoArgUseCase
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext

@Inject
internal class RequestAuthorizationUrlUseCase(
    private val codeChallenge: CodeChallenge,
    private val spotifyAccountService: SpotifyAccountService,
    @State private val state: String,
    private val coroutineContextProvider: CoroutineContextProvider,
) : NoArgUseCase<String> {
    override suspend fun invoke(): String =
        withContext(coroutineContextProvider.default) {
            val challenge = codeChallenge.challenge()
            return@withContext spotifyAccountService.getAuthUrl(
                state = state,
                codeChallenge = challenge,
            )
        }
}
