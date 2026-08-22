// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization

import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.annotation.RequiresApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

@ContributesBinding(AppScope::class)
internal class DefaultKeyProvider : KeyProvider {
    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun getKey(): ByteArray {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply { load(null) }
        if (keyStore.containsAlias(ALIAS)) {
            val existingKey = keyStore.getKey(ALIAS, null) as SecretKey
            return existingKey.encoded
        }

        val keyGen = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
        val spec =
            KeyGenParameterSpec.Builder(
                    ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT,
                )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(128)
                .build()

        keyGen.init(spec)
        return keyGen.generateKey().encoded
    }

    private companion object {
        private const val ALIAS = "dev.whosnickdoglio.spot"
        private const val ANDROID_KEY_STORE = "AndroidKeyStore"
    }
}
