// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import androidx.datastore.core.DataStore
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.first

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
internal class DefaultSpotifyTokenProvider(private val tokenStore: DataStore<Tokens>) :
    SpotifyTokenProvider {

    override suspend fun getTokens(): Tokens = tokenStore.data.first()

    override suspend fun putTokens(tokens: Tokens) {
        tokenStore.updateData {
            tokens
        }
    }
}
