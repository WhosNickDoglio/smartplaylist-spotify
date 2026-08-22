// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whyoleg.cryptography.algorithms.AES
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.security.GeneralSecurityException
import kotlinx.coroutines.withContext

/**
 * https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:datastore/datastore-tink/src/commonMain/kotlin/androidx/datastore/tink/AeadSerializer.kt
 * Heavily inspired from AeadSerializer
 */
// TODO decouple from JVM?
//
public class EncryptedSerializer<T>(
    private val aesGcm: AES.GCM,
    private val keyProvider: KeyProvider,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val wrappedSerializer: Serializer<T>,
    private val associatedData: ByteArray = byteArrayOf(),
) : Serializer<T> {

    override val defaultValue: T = wrappedSerializer.defaultValue

    override suspend fun readFrom(input: InputStream): T =
        withContext(coroutineContextProvider.io) {
            val encrypted = input.readBytes()
            val cipher =
                aesGcm
                    .keyDecoder()
                    .decodeFromByteArray(
                        AES.Key.Format.RAW,
                        keyProvider.getKey(),
                    )
                    .cipher()
            val decrypted =
                if (encrypted.isNotEmpty()) {
                    try {
                        cipher.decrypt(encrypted, associatedData)
                    } catch (e: GeneralSecurityException) {
                        throw CorruptionException("Decryption failed", e)
                    }
                } else {
                    encrypted
                }
            return@withContext wrappedSerializer.readFrom(ByteArrayInputStream(decrypted))
        }

    override suspend fun writeTo(t: T, output: OutputStream): Unit =
        withContext(coroutineContextProvider.io) {
            val cipher =
                aesGcm
                    .keyDecoder()
                    .decodeFromByteArray(
                        AES.Key.Format.RAW,
                        keyProvider.getKey(),
                    )
                    .cipher()
            val byteArrayOutputStream = ByteArrayOutputStream()
            wrappedSerializer.writeTo(t, byteArrayOutputStream)
            val encrypted = cipher.encrypt(byteArrayOutputStream.toByteArray(), associatedData)
            output.write(encrypted)
        }
}
