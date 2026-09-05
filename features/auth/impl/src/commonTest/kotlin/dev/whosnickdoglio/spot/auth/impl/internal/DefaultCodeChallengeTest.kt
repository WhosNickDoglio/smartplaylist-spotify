// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl.internal

import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.coroutineContextProvider
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.SHA256
import dev.whyoleg.cryptography.operations.Hasher
import kotlin.io.encoding.Base64
import kotlinx.coroutines.test.TestScope

class DefaultCodeChallengeTest {

    private fun TestScope.createChallenge(
        verifier: String = "",
        hasher: Hasher = CryptographyProvider.Default.get(SHA256).hasher(),
        encoder: Base64 = Base64.UrlSafe.withPadding(Base64.PaddingOption.ABSENT),
        coroutineContextProvider: CoroutineContextProvider = coroutineContextProvider(),
    ): CodeChallenge =
        DefaultCodeChallenge(
            verifier = verifier,
            hasher = hasher,
            encoder = encoder,
            coroutineContextProvider = coroutineContextProvider,
        )
}
