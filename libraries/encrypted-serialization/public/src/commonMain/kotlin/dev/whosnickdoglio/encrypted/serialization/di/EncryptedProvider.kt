// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization.di

import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.operations.IvAuthenticatedCipher
import dev.whyoleg.cryptography.operations.KeyGenerator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.withContext

@ContributesTo(AppScope::class)
public interface EncryptedProvider {

    @SingleIn(AppScope::class)
    @Provides
    public fun provideAead(): KeyGenerator<AES.GCM.Key> =
        CryptographyProvider.Default.get(AES.GCM).keyGenerator()

    @SingleIn(AppScope::class)
    @Provides
    public suspend fun provideCipher(
        keyGen: KeyGenerator<AES.GCM.Key>,
        coroutineContextProvider: CoroutineContextProvider,
    ): IvAuthenticatedCipher =
        withContext(coroutineContextProvider.default) {
            keyGen.generateKey().cipher()
        }
}
