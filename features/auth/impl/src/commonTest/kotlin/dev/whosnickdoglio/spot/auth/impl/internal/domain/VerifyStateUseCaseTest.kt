// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.auth.impl.internal.domain

import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.coroutineContextProvider
import kotlinx.coroutines.test.TestScope

class VerifyStateUseCaseTest {

    private fun TestScope.createUseCase(
        state: String = "",
        coroutineContextProvider: CoroutineContextProvider = coroutineContextProvider(),
    ): VerifyStateUseCase =
        VerifyStateUseCase(
            state = state,
            coroutineContextProvider = coroutineContextProvider,
        )
}
