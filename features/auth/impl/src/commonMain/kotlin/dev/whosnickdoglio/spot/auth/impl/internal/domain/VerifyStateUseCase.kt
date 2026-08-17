// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal.domain

import dev.whosnickdoglio.spot.auth.impl.di.State
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.usecase.UseCase
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.withContext

@Inject
internal class VerifyStateUseCase(
    @State private val state: String,
    private val coroutineContextProvider: CoroutineContextProvider,
) : UseCase<String, Boolean> {
    override suspend fun invoke(arg: String): Boolean =
        withContext(coroutineContextProvider.default) {
            return@withContext arg == state
        }
}
