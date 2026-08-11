// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.concurrency.tesing

import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope

public class TestingCoroutineContextProvider(
    override val main: CoroutineContext = StandardTestDispatcher(),
    override val mainImmediate: CoroutineContext = StandardTestDispatcher(),
    override val io: CoroutineContext = StandardTestDispatcher(),
    override val default: CoroutineContext = StandardTestDispatcher(),
    override val db: CoroutineContext = StandardTestDispatcher(),
) : CoroutineContextProvider

public fun TestScope.coroutineContextProvider(
    main: CoroutineContext = testScheduler,
    mainImmediate: CoroutineContext = testScheduler,
    io: CoroutineContext = testScheduler,
    default: CoroutineContext = testScheduler,
    db: CoroutineContext = testScheduler,
): CoroutineContextProvider =
    TestingCoroutineContextProvider(
        main = main,
        mainImmediate = mainImmediate,
        io = io,
        default = default,
        db = db,
    )
