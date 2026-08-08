// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.usecase

import kotlinx.coroutines.flow.Flow

public fun interface UseCase<ARG : Any, RETURN : Any> {
    public suspend operator fun invoke(arg: ARG): RETURN
}

public interface ObservableUseCase<ARG : Any, RETURN : Any> {
    public suspend operator fun invoke(arg: ARG): Flow<RETURN>
}

public fun interface NoArgUseCase<RETURN : Any> {
    public suspend operator fun invoke(): RETURN
}

public fun interface NoArgObservableUseCase<RETURN : Any> {
    public suspend operator fun invoke(): Flow<RETURN>
}
