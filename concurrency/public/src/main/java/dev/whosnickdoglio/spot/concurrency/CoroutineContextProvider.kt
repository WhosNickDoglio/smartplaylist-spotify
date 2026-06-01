// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.concurrency

import kotlin.coroutines.CoroutineContext

// https://stackoverflow.com/a/78509710
public interface CoroutineContextProvider {
    public val main: CoroutineContext
    public val mainImmediate: CoroutineContext
    public val io: CoroutineContext
    public val default: CoroutineContext
    public val db: CoroutineContext
}
