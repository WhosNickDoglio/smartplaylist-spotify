// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.concurrency.internal

import dev.whosnickdoglio.spot.concurrency.ApplicationScope
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@ContributesTo(AppScope::class)
public interface ApplicationScopeProvider {

    @ApplicationScope
    @SingleIn(AppScope::class)
    @Provides
    public fun provideApplicationScope(contextProvider: CoroutineContextProvider): CoroutineScope =
        CoroutineScope(SupervisorJob() + contextProvider.default)
}
