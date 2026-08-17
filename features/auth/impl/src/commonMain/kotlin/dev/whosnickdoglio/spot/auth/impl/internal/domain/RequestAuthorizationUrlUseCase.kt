// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal.domain

import dev.whosnickdoglio.spot.auth.impl.di.State
import dev.whosnickdoglio.spot.auth.impl.internal.CodeChallenge
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.SpotifyAccountService
import dev.whosnickdoglio.spot.usecase.NoArgUseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext

public fun interface RequestAuthorizationUrlUseCase : NoArgUseCase<String>

@ContributesBinding(AppScope::class)
internal class DefaultRequestAuthorizationUrlUseCase(
    private val codeChallenge: CodeChallenge,
    private val spotifyAccountService: SpotifyAccountService,
    @State private val state: String,
    private val coroutineContextProvider: CoroutineContextProvider,
) : RequestAuthorizationUrlUseCase {
    override suspend fun invoke(): String =
        withContext(coroutineContextProvider.default) {
            val challenge = codeChallenge.challenge()
            return@withContext spotifyAccountService.getAuthUrl(
                state = state,
                codeChallenge = challenge,
            )
        }
}
