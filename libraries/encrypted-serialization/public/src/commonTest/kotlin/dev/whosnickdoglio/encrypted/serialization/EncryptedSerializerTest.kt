// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import assertk.all
import assertk.assertFailure
import assertk.assertThat
import assertk.assertions.cause
import assertk.assertions.hasMessage
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import assertk.assertions.isNotEqualTo
import dev.whosnickdoglio.spot.concurrency.CoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.TestingCoroutineContextProvider
import dev.whosnickdoglio.spot.concurrency.tesing.coroutineContextProvider
import dev.whyoleg.cryptography.CryptographyProvider
import dev.whyoleg.cryptography.algorithms.AES
import dev.whyoleg.cryptography.operations.IvAuthenticatedCipher
import dev.zacsweers.metro.SuspendLazy
import dev.zacsweers.metro.suspendLazy
import java.io.InputStream
import java.io.OutputStream
import kotlinx.coroutines.test.runTest
import kotlinx.io.Buffer
import kotlinx.io.asInputStream
import kotlinx.io.asOutputStream
import kotlinx.io.bytestring.decodeToString
import kotlinx.io.snapshot
import org.junit.Test

// https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:datastore/datastore-tink/src/commonTest/kotlin/androidx/datastore/tink/AeadSerializerTest.kt
class EncryptedSerializerTest {

    @Test
    fun `serialization with no encryption`() = runTest {
        // Arrange.
        val buffer = Buffer()

        // Serialize.
        val serializer = StringSerializer()
        serializer.writeTo("Unserialized data", buffer.asOutputStream())

        // Verify that the data is not encrypted.
        assertThat(buffer.snapshot().decodeToString()).isEqualTo("Unserialized data")

        // Deserialize.
        val deserializedBytes = serializer.readFrom(buffer.asInputStream())
        assertThat(deserializedBytes).isEqualTo("Unserialized data")
    }

    @Test
    fun `serialization with encryption`() = runTest {
        val aeadSerializer =
            createSerializer(
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = "associated_data".encodeToByteArray(),
            )
        val buffer = Buffer()

        // Serialize.
        aeadSerializer.writeTo("Unencrypted data", buffer.asOutputStream())

        // Verify that the data is encrypted.
        assertThat(buffer.snapshot().decodeToString()).isNotEqualTo("Unencrypted data")

        // Deserialize.
        val decryptedBytes = aeadSerializer.readFrom(buffer.asInputStream())
        assertThat(decryptedBytes).isEqualTo("Unencrypted data")
    }

    @Test
    fun `serialization with encryption and associated data`() = runTest {
        val aeadSerializer =
            createSerializer(
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = "associated_data".encodeToByteArray(),
            )
        val buffer = Buffer()

        // Serialize.
        aeadSerializer.writeTo("Unencrypted data", buffer.asOutputStream())

        // Verify that the data is encrypted.
        assertThat(buffer.snapshot().decodeToString()).isNotEqualTo("Unencrypted data")

        // Deserialize.
        val decryptedBytes = aeadSerializer.readFrom(buffer.asInputStream())
        assertThat(decryptedBytes).isEqualTo("Unencrypted data")
    }

    @Test
    fun `serialization with encryption and incorrect associated data`() = runTest {
        val aeadSerializer =
            createSerializer(
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = "associated_data".encodeToByteArray(),
            )
        val buffer = Buffer()

        // Serialize.
        aeadSerializer.writeTo("Unencrypted data", buffer.asOutputStream())

        // Deserialize with incorrect associated data should throw.
        val incorrectAeadSerializer =
            createSerializer(
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = "incorrect_associated_data".encodeToByteArray(),
            )

        assertFailure {
            incorrectAeadSerializer.readFrom(buffer.asInputStream())
        }
            .apply {
                hasMessage("Decryption failed")
                cause().all {
                    this@apply.isInstanceOf<CorruptionException>()
                }
            }
    }

    @Test
    fun `serialization with encryption and incorrect key`() = runTest {
        val associatedData = "associated_data".encodeToByteArray()
        val aeadSerializer =
            createSerializer(
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = associatedData,
            )
        val buffer = Buffer()

        // Serialize.
        aeadSerializer.writeTo("Unencrypted data", buffer.asOutputStream())

        // Deserialize with incorrect key should throw.
        val wrongAead =
            CryptographyProvider.Default.get(AES.GCM).keyGenerator().generateKey().cipher()
        val incorrectAeadSerializer =
            createSerializer(
                suspendLazy { wrongAead },
                coroutineContextProvider = this.coroutineContextProvider(),
                associatedData = associatedData,
            )

        assertFailure {
            incorrectAeadSerializer.readFrom(buffer.asInputStream())
        }
            .apply {
                hasMessage("Decryption failed")
                cause().all {
                    this@apply.isInstanceOf<CorruptionException>()
                }
            }
    }
}

private fun createSerializer(
    cipher: SuspendLazy<IvAuthenticatedCipher> = suspendLazy {
        CryptographyProvider.Default.get(AES.GCM).keyGenerator().generateKey().cipher()
    },
    coroutineContextProvider: CoroutineContextProvider = TestingCoroutineContextProvider(),
    wrappedSerializer: Serializer<String> = StringSerializer(),
    associatedData: ByteArray = byteArrayOf(),
): EncryptedSerializer<String> =
    EncryptedSerializer(
        cipherProvider = cipher,
        coroutineContextProvider = coroutineContextProvider,
        wrappedSerializer = wrappedSerializer,
        associatedData = associatedData,
    )

private class StringSerializer : Serializer<String> {
    override suspend fun readFrom(input: InputStream): String {
        return input.readBytes().decodeToString()
    }

    override suspend fun writeTo(t: String, output: OutputStream) {
        output.write(t.encodeToByteArray())
    }

    override val defaultValue: String = ""
}
