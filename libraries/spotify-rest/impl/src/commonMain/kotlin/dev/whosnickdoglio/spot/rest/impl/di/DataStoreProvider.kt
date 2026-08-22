// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.encrypted.serialization.KeyProvider
import dev.whosnickdoglio.spot.concurrency.ApplicationScope
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.whosnickdoglio.spot.rest.impl.auth.TokensFileStorageFactory
import dev.whyoleg.cryptography.algorithms.AES
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ExperimentalMetroCoroutinesApi
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope

@ContributesTo(AppScope::class)
public interface DataStoreProvider {

    @OptIn(ExperimentalMetroCoroutinesApi::class)
    @SingleIn(AppScope::class)
    @Provides
    public fun provideAuthDataStore(
        aesGcm: AES.GCM,
        coroutineContextProvider: CoroutineContextProvider,
        keyProvider: KeyProvider,
        @ApplicationScope scope: CoroutineScope,
        tokensFileStorageFactory: TokensFileStorageFactory,
        wrappedSerializer: Serializer<Tokens>,
    ): DataStore<Tokens> =
        DataStore.Builder(
                storage =
                    tokensFileStorageFactory.create(
                        serializer = wrappedSerializer,
                        // serializer = EncryptedSerializer(
                        //     aesGcm = aesGcm,
                        //     keyProvider = keyProvider,
                        //     coroutineContextProvider = coroutineContextProvider,
                        //     wrappedSerializer = wrappedSerializer,
                        //     associatedData = TOKENS_FILE.encodeToByteArray(),
                        // ),
                        fileName = TOKENS_FILE,
                    ),
                context = scope.coroutineContext,
            )
            // .setCorruptionHandler() // TODO
            .build()
}

private const val TOKENS_FILE = "tokens.db"
