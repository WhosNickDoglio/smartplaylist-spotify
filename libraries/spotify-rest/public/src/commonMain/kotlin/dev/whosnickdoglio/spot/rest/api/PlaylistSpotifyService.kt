// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.api

import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spot.rest.SpotifyErrorResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

public interface PlaylistSpotifyService {
    // https://developer.spotify.com/documentation/web-api/reference/create-playlist
    public suspend fun createPlaylist()

    // https://developer.spotify.com/documentation/web-api/reference/get-a-list-of-current-users-playlists
    public suspend fun requestPlaylists(
        limit: Int = 50,
        offset: Int = 0,
    ): ApiResult<RequestPlaylistResponse, SpotifyErrorResponse>

    // https://developer.spotify.com/documentation/web-api/reference/get-playlist
    public suspend fun requestPlaylist()

    // https://developer.spotify.com/documentation/web-api/reference/reorder-or-replace-playlists-items
    public suspend fun updatePlaylist()

    // https://developer.spotify.com/documentation/web-api/reference/add-items-to-playlist
    public suspend fun addItemsToPlaylist()

    // https://developer.spotify.com/documentation/web-api/reference/remove-items-playlist
    public suspend fun removeItemsFromPlaylist()

    // https://developer.spotify.com/documentation/web-api/reference/change-playlist-details
    public suspend fun changePlaylistDetails()
}

@Serializable
public data class RequestPlaylistResponse(
    @SerialName("href") val href: String? = "",
    @SerialName("items") val items: List<Item>? = listOf(),
    @SerialName("limit") val limit: Int? = 0,
    @SerialName("next") val next: String? = "",
    @SerialName("offset") val offset: Int? = 0,
    @SerialName("previous") val previous: String? = "",
    @SerialName("total") val total: Int? = 0,
)

@Serializable
public data class Item(
    @SerialName("collaborative") val collaborative: Boolean? = false,
    @SerialName("description") val description: String? = "",
    @SerialName("external_urls") val externalUrls: ExternalUrls? = ExternalUrls(),
    @SerialName("href") val href: String? = "",
    @SerialName("id") val id: String? = "",
    @SerialName("images") val images: List<Image>? = listOf(),
    @SerialName("items") val items: Items? = Items(),
    @SerialName("name") val name: String? = "",
    @SerialName("owner") val owner: Owner? = Owner(),
    @SerialName("public") val `public`: Boolean? = false,
    @SerialName("snapshot_id") val snapshotId: String? = "",
    @SerialName("tracks") val tracks: Tracks? = Tracks(),
    @SerialName("type") val type: String? = "",
    @SerialName("uri") val uri: String? = "",
)

@Serializable public data class ExternalUrls(@SerialName("spotify") val spotify: String? = null)

@Serializable
public data class Image(
    @SerialName("height") val height: Int? = null,
    @SerialName("url") val url: String? = null,
    @SerialName("width") val width: Int? = null,
)

@Serializable
public data class Items(
    @SerialName("href") val href: String? = null,
    @SerialName("total") val total: Int? = null,
)

@Serializable
public data class Owner(
    @SerialName("display_name") val displayName: String? = "",
    @SerialName("external_urls") val externalUrls: ExternalUrls? = ExternalUrls(),
    @SerialName("href") val href: String? = "",
    @SerialName("id") val id: String? = "",
    @SerialName("type") val type: String? = "",
    @SerialName("uri") val uri: String? = "",
)

@Serializable
public data class Tracks(
    @SerialName("href") val href: String? = null,
    @SerialName("total") val total: Int? = null,
)
