// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.db.impl.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import dev.whosnickdoglio.spot.db.impl.SpotDb
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface DriverProvider {

    @Provides
    public fun driver(): SqlDriver =
        JdbcSqliteDriver(
            url = "jdbc:sqlite:${DB_NAME}",
            schema = SpotDb.Schema,
        )
}
