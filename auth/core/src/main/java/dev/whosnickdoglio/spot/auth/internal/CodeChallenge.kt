// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.internal

import dev.whyoleg.cryptography.operations.Hasher
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.io.encoding.Base64

public fun interface CodeChallenge {
    public suspend fun challenge(): String
}

@ContributesBinding(AppScope::class)
internal class DefaultCodeChallenge(private val hasher: Hasher) : CodeChallenge {
    override suspend fun challenge(): String {
        val code = codeVerifier()
        val hash = hasher.hash(code.toByteArray())
        return Base64.encode(hash)
    }

    @Suppress("MagicNumber")
    private fun codeVerifier(): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..128).map { allowedChars.random() }.joinToString("")
    }
}
