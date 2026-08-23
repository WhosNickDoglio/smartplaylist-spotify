// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.impl.auth

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.rest.auth.Tokens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

@ContributesBinding(AppScope::class)
internal class TokensSerializer(private val coroutineContextProvider: CoroutineContextProvider) :
    Serializer<Tokens?> {

    override val defaultValue: Tokens? = null

    override suspend fun readFrom(input: InputStream): Tokens =
        withContext(coroutineContextProvider.io) {
            try {
                return@withContext Json.decodeFromString(
                    Tokens.serializer(),
                    input.readBytes().decodeToString(),
                )
            } catch (serialization: SerializationException) {
                throw CorruptionException("Unable to read Tokens", serialization)
            }
        }

    override suspend fun writeTo(t: Tokens?, output: OutputStream) {
        withContext(coroutineContextProvider.io) {
            if (t != null) {
                output.write(Json.encodeToString(Tokens.serializer(), t).encodeToByteArray())
            }
        }
    }
}
