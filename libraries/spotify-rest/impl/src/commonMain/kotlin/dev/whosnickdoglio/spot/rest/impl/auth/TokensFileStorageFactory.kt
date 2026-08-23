// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import androidx.datastore.core.FileStorage
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.rest.auth.Tokens

public fun interface TokensFileStorageFactory {
    public fun create(
        serializer: Serializer<Tokens?>,
        fileName: String,
    ): FileStorage<Tokens?>
}
