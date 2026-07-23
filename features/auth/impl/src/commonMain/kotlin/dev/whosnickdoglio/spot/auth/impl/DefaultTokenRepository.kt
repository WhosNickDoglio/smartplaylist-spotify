// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import dev.whosnickdoglio.spot.auth.TokenRepository
import dev.whosnickdoglio.spot.auth.Tokens
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
internal class DefaultTokenRepository : TokenRepository {

    private var tokens: Tokens? = null

    override fun putTokens(tokens: Tokens) {
        this.tokens = tokens
    }

    override fun getTokens(): Tokens? = tokens
}
