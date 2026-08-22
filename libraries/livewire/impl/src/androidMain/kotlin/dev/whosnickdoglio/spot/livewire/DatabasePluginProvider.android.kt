// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.livewire

import android.content.Context
import com.livewire.plugin.database.DatabasePlugin
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface DatabasePluginProvider {

    @Provides
    public fun provideDatabasePlugin(context: Context): DatabasePlugin = DatabasePlugin(context)
}
