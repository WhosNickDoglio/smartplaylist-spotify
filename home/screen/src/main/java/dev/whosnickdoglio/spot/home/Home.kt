// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.presenter.Presenter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(HomeScreen::class, AppScope::class)
@Composable
internal fun HomeScreen(state: HomeScreen.State, modifier: Modifier = Modifier) {}

@CircuitInject(HomeScreen::class, AppScope::class)
@Inject
internal class HomePresenter() : Presenter<HomeScreen.State> {

    @Composable override fun present(): HomeScreen.State = HomeScreen.State.Authenticated
}
