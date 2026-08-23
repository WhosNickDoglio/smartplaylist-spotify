// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.rest.auth

import com.google.crypto.tink.KeysetHandle

public fun interface KeysetHandleProvider {
    public fun provide(): KeysetHandle
}
