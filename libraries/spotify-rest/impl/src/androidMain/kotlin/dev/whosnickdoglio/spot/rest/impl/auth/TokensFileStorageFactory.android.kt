// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import android.content.Context
import androidx.datastore.core.FileStorage
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class DefaultTokensFileStorageFactory(private val context: Context) :
    TokensFileStorageFactory {
    override fun create(serializer: Serializer<Tokens>, fileName: String): FileStorage<Tokens> =
        FileStorage(
            serializer = serializer,
            produceFile = { context.filesDir.resolve(fileName) },
        )
}
