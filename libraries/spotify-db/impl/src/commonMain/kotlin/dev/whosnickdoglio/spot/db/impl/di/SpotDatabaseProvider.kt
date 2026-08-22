// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.db.impl.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import dev.whosnickdoglio.spot.SmartPlaylist
import dev.whosnickdoglio.spot.SmartPlaylistQueries
import dev.whosnickdoglio.spot.db.impl.SpotDb
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.datetime.LocalDateTime

@ContributesTo(AppScope::class)
public interface SpotDatabaseProvider {

    @Provides
    @SingleIn(AppScope::class)
    public fun database(
        driver: SqlDriver,
        adapter: ColumnAdapter<LocalDateTime, String>,
    ): SpotDb = SpotDb(driver, SmartPlaylist.Adapter(lastSyncAdapter = adapter))

    @Provides
    public fun smartPlaylistQueries(database: SpotDb): SmartPlaylistQueries =
        database.smartPlaylistQueries
}

internal const val DB_NAME = "spot.db"
