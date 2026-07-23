// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.di

import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.algorithms.SHA256
import dev.whyoleg.cryptography.operations.Hasher
import dev.whyoleg.cryptography.operations.IvAuthenticatedCipher
import dev.whyoleg.cryptography.operations.KeyGenerator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import dev.zacsweers.metro.SingleIn
import kotlin.io.encoding.Base64
import kotlin.uuid.Uuid
import kotlinx.coroutines.withContext

@ContributesTo(AppScope::class)
public interface AuthProviders {

    @Provides
    public fun provideCryptographyHasher(): Hasher =
        CryptographyProvider.Default.get(SHA256).hasher()

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
            return@withContext keyGen.generateKey().cipher()
        }

    @Provides
    public fun provideBase64Encoder(): Base64 =
        Base64.UrlSafe.withPadding(Base64.PaddingOption.ABSENT)

    @Suppress("MagicNumber")
    @CodeVerifier
    @SingleIn(AppScope::class)
    @Provides
    public fun provideCodeVerifier(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        val code = (1..128).map { allowedChars.random() }.joinToString("")
        return code
    }

    @State
    @SingleIn(AppScope::class)
    @Provides
    public fun provideState(): String = Uuid.random().toString()

    // @SingleIn(AppScope::class)
    // @Provides
    // public fun provideAuthDataStore(
    //     @ApplicationScope scope: CoroutineScope,
    //     cipherProvider: SuspendLazy<IvAuthenticatedCipher>,
    // ): DataStore<String> {
    //     return DataStore.Builder(
    //         storage = FileStorage<String>(
    //             serializer = EncryptedSerializer(
    //                 cipherProvider = cipherProvider,
    //                 wrappedSerializer = (),
    //                 associatedData = TODO()
    //             ),
    //             produceFile = {}
    //         ),
    //         context = scope.coroutineContext)
    //         // .setCorruptionHandler() // TODO
    //         .build()
    // }
}

@Qualifier public annotation class CodeVerifier

@Qualifier public annotation class State
