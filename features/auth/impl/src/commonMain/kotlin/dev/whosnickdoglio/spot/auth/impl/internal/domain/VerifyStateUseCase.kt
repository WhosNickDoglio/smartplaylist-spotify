// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal.domain

import dev.whosnickdoglio.spot.auth.impl.di.State
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.usecase.UseCase
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.withContext

public interface VerifyStateUseCase : UseCase<String, Boolean>

@ContributesBinding(AppScope::class)
internal class DefaultVerifyStateUseCase(
    @State private val state: String,
    private val coroutineContextProvider: CoroutineContextProvider,
) : VerifyStateUseCase {
    override suspend fun invoke(arg: String): Boolean =
        withContext(coroutineContextProvider.default) {
            return@withContext arg == state
        }
}
