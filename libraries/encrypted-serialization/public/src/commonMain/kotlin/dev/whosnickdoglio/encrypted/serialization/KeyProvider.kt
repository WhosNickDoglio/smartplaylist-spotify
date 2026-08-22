// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.encrypted.serialization

public fun interface KeyProvider {
    public suspend fun getKey(): ByteArray
}
