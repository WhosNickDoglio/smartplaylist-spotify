// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.di

import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA512
import dev.whyoleg.cryptography.operations.Hasher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface CryptographyProviders {

    @Provides
    public fun provideCryptographyHasher(): Hasher =
        CryptographyProvider.Default.get(SHA512).hasher()
}
