// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
internal class DefaultKeyProvider : KeyProvider {
    override suspend fun getKey(): ByteArray {
        TODO("Not yet implemented")
    }
}
