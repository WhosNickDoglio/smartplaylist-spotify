// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal

import dev.whosnickdoglio.spot.auth.impl.di.CodeVerifier
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whyoleg.cryptography.operations.Hasher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.io.encoding.Base64
import kotlinx.coroutines.withContext

internal fun interface CodeChallenge {
    suspend fun challenge(): String
}

@ContributesBinding(AppScope::class)
internal class DefaultCodeChallenge(
    @CodeVerifier private val verifier: String,
    private val hasher: Hasher,
    private val encoder: Base64,
    private val coroutineContextProvider: CoroutineContextProvider,
) : CodeChallenge {
    override suspend fun challenge(): String =
        withContext(coroutineContextProvider.default) {
            val hash = hasher.hash(verifier.toByteArray())
            return@withContext encoder.encode(hash)
        }
}
