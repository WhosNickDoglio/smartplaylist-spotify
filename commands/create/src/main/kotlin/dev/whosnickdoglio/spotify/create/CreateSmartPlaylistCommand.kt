// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.create

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import dev.whosnickdoglio.spotify.commands.RootSubcommand
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

// TODO should this also handle updating playlists
@Inject
@RootSubcommand
@ContributesIntoSet(AppScope::class)
internal class CreateSmartPlaylistCommand : SuspendingCliktCommand(name = "smart-playlist") {

    override fun help(context: Context): String =
        "Create playlist given a specific set of criteria."

    override suspend fun run() {
        TODO("Not yet implemented")
    }

    sealed interface PlaylistType {
        data class Year(val year: Int) : PlaylistType

        data class LastXDays(val days: DayOptions) : PlaylistType
    }

    enum class DayOptions {
        LAST_7_DAYS,
        LAST_14_DAYS,
        LAST_30_DAYS,
        LAST_90_DAYS,
    }
}
