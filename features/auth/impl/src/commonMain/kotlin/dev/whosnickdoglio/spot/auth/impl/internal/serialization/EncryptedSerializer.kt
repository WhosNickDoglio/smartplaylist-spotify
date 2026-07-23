// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl.internal.serialization

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whyoleg.cryptography.operations.IvAuthenticatedCipher
import dev.zacsweers.metro.SuspendLazy
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
// TODO assisted injection
// TODO decouple from JVM?
public class EncryptedSerializer<T>(
    private val cipherProvider: SuspendLazy<IvAuthenticatedCipher>,
    private val coroutineContextProvider: CoroutineContextProvider,
    private val wrappedSerializer: Serializer<T>,
    private val associatedData: ByteArray = byteArrayOf(),
) : Serializer<T> {
    override val defaultValue: T = wrappedSerializer.defaultValue

    override suspend fun readFrom(input: InputStream): T =
        withContext(coroutineContextProvider.io) {
            val cipher = cipherProvider.await()
            val encrypted = input.readBytes()
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
            val cipher = cipherProvider.await()
            val byteArrayOutputStream = ByteArrayOutputStream()
            wrappedSerializer.writeTo(t, byteArrayOutputStream)
            val encrypted = cipher.encrypt(byteArrayOutputStream.toByteArray(), associatedData)
            output.write(encrypted)
        }
}
