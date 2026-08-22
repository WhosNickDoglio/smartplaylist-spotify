// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.db.impl

import dev.whosnickdoglio.spot.SmartPlaylistQueries
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.db.LocalSmartPlaylistRepository
import dev.whosnickdoglio.spot.db.SmartPlaylist
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.time.Clock
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@ContributesBinding(AppScope::class)
internal class DefaultSmartPlaylistRepository(
    private val queries: SmartPlaylistQueries,
    private val contextProvider: CoroutineContextProvider,
) : LocalSmartPlaylistRepository {
    override suspend fun getPlaylists(): List<SmartPlaylist> =
        withContext(contextProvider.db) {
            return@withContext queries.selectAll().executeAsList().map { SmartPlaylist("") }
        }

    override suspend fun createPlaylist(name: String, id: String, url: String?): Unit =
        withContext(contextProvider.db) {
            queries
                .insert(
                    id = name,
                    name = id,
                    lastSync = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    imageUrl = url,
                )
                .await()
        }
}
