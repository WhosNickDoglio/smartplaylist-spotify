// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.concurrency

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers

@Suppress("InjectDispatcher")
@ContributesBinding(AppScope::class)
internal class DesktopCoroutineContextProvider : CoroutineContextProvider {
    override val main: CoroutineContext = Dispatchers.Main
    override val mainImmediate: CoroutineContext = Dispatchers.Main.immediate
    override val io: CoroutineContext = Dispatchers.IO
    override val default: CoroutineContext = Dispatchers.Default
    override val db: CoroutineContext = Dispatchers.IO.limitedParallelism(1)
}
