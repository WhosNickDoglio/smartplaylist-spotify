// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl

import com.slack.eithernet.ApiResult
import com.slack.eithernet.integration.ktor.apiResultOf
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import dev.whosnickdoglio.spot.rest.api.PlaylistSpotifyService
import dev.whosnickdoglio.spot.rest.api.RequestPlaylistResponse
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.ktor.client.HttpClient
import io.ktor.client.request.request
import io.ktor.http.HttpMethod
import io.ktor.util.appendAll

internal const val SPOTIFY_BASE_URL = "https://api.spotify.com/v1"

@ContributesBinding(AppScope::class)
internal class DefaultPlaylistSpotifyService(private val httpClient: HttpClient) :
    PlaylistSpotifyService {
    override suspend fun createPlaylist() {
        TODO("Not yet implemented")
    }

    override suspend fun requestPlaylists(
        limit: Int,
        offset: Int,
    ): ApiResult<RequestPlaylistResponse, SpotifyErrorResponse> = httpClient.apiResultOf {
        request("${SPOTIFY_BASE_URL}/me/playlists") {
            method = HttpMethod.Get
            url.parameters.appendAll(
                mapOf(
                    "limit" to limit.toString(),
                    "offset" to offset.toString(),
                )
            )
        }
    }

    override suspend fun requestPlaylist() {
        TODO("Not yet implemented")
    }

    override suspend fun updatePlaylist() {
        TODO("Not yet implemented")
    }

    override suspend fun addItemsToPlaylist() {
        TODO("Not yet implemented")
    }

    override suspend fun removeItemsFromPlaylist() {
        TODO("Not yet implemented")
    }

    override suspend fun changePlaylistDetails() {
        TODO("Not yet implemented")
    }
}
