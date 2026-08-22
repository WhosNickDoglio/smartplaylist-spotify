// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.db.impl

import app.cash.sqldelight.ColumnAdapter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.datetime.LocalDateTime

@ContributesBinding(AppScope::class)
internal class TimestampColumnAdapter : ColumnAdapter<LocalDateTime, String> {

    override fun decode(databaseValue: String): LocalDateTime = LocalDateTime.parse(databaseValue)

    override fun encode(value: LocalDateTime): String = value.toString()
}
