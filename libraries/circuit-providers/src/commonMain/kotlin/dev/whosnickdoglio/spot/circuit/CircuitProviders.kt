// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.circuit

import com.slack.circuit.foundation.Circuit
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.ui.Ui
import com.slack.circuit.subcircuit.SubCircuit
import com.slack.circuit.subcircuit.SubPresenterFactory
import com.slack.circuit.subcircuit.SubUiFactory
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface CircuitProviders {

    @Multibinds public fun presenterFactories(): Set<Presenter.Factory>

    @Multibinds public fun viewFactories(): Set<Ui.Factory>

    @Multibinds(allowEmpty = true) public fun subPresenterFactories(): Set<SubPresenterFactory>

    @Multibinds(allowEmpty = true) public fun subViewFactories(): Set<SubUiFactory>

    @Provides
    public fun provideCircuit(
        uiFactories: Set<Ui.Factory>,
        presenterFactories: Set<Presenter.Factory>,
    ): Circuit =
        Circuit.Builder()
            .addUiFactories(uiFactories)
            .addPresenterFactories(presenterFactories)
            .build()

    @Provides
    public fun provideSubCircuit(
        presenterFactories: Set<SubPresenterFactory>,
        uiFactories: Set<SubUiFactory>,
    ): SubCircuit =
        SubCircuit.builder()
            .addPresenterFactories(presenterFactories)
            .addUiFactories(uiFactories)
            .build()
}
