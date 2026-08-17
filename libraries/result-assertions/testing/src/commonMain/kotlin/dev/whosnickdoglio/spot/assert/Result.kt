// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.spot.assert

import assertk.Assert
import assertk.assertions.isTrue
import assertk.assertions.prop
import assertk.assertions.support.expected
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.getOrElse

public fun <V, E> Assert<Result<V, E>>.isOkay() {
    prop(Result<V, E>::isOk).isTrue()
}

public fun <V, E> Assert<Result<V, E>>.hasOkayValue(): Assert<V> = transform { actual ->
    actual.getOrElse { expected("to be okay") }
}

public fun <V, E> Assert<Result<V, E>>.isError() {
    prop(Result<V, E>::isErr).isTrue()
}
