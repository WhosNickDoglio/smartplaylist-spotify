// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.di

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA256
import dev.whyoleg.cryptography.operations.Hasher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Qualifier
import dev.zacsweers.metro.SingleIn
import kotlin.io.encoding.Base64
import kotlin.uuid.Uuid

@ContributesTo(AppScope::class)
public interface AuthProviders {

    @Provides
    public fun provideCryptographyHasher(): Hasher =
        CryptographyProvider.Default.get(SHA256).hasher()

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
}

@Qualifier public annotation class CodeVerifier

@Qualifier public annotation class State
