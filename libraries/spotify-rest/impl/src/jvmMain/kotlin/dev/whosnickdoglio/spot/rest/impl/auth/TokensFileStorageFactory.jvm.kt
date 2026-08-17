// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import androidx.datastore.core.FileStorage
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.io.File

@ContributesBinding(AppScope::class)
internal class DefaultTokensFileStorageFactory() : TokensFileStorageFactory {
    override fun create(serializer: Serializer<Tokens>, fileName: String) =
        FileStorage(
            serializer = serializer,
            produceFile = { File(System.getProperty("java.io.tmpdir"), fileName) },
        )
}
