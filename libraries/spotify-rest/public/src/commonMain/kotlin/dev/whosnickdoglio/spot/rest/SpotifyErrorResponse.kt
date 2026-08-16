// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// {"error":"invalid_grant","error_description":"code_verifier was incorrect"}
@Serializable
public data class SpotifyErrorResponse(
    val error: String,
    @SerialName("error_description") val message: String,
)
