// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.home

// import dev.whosnickdoglio.spot.auth.AuthScreen
// import dev.whosnickdoglio.spot.auth.AuthenticationState
// import dev.whosnickdoglio.spot.auth.SpotifyAuthenticator
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.NavigableCircuitContent
import com.slack.circuit.foundation.navstack.rememberSaveableNavStack
import com.slack.circuit.foundation.rememberCircuitNavigator
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuitx.gesturenavigation.GestureNavigationDecorationFactory
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject

@CircuitInject(HomeScreen::class, AppScope::class)
@Composable
internal fun HomeScreen(state: HomeScreen.State, modifier: Modifier = Modifier) {
    val navStack =
        rememberSaveableNavStack(
            root =
                when (state) {
                    is HomeScreen.State.Authenticated -> state.screen
                    is HomeScreen.State.Unauthenticated -> state.screen
                }
        )
    val navigator = rememberCircuitNavigator(navStack, {})
    NavigableCircuitContent(
        navigator = navigator,
        navStack = navStack,
        modifier = modifier.safeDrawingPadding(),
        decoratorFactory =
            remember(navigator) {
                GestureNavigationDecorationFactory(onBackInvoked = navigator::pop)
            },
    )
}

@CircuitInject(HomeScreen::class, AppScope::class)
@Inject
internal class HomePresenter() : Presenter<HomeScreen.State> {

    @Composable
    override fun present(): HomeScreen.State {
        // val authenticationState by authenticator.authenticationState.collectAsState()
        //
        // val state =
        //     when (authenticationState) {
        //         AuthenticationState.AUTHENTICATED ->
        // HomeScreen.State.Authenticated(PlaylistScreen)
        //         AuthenticationState.UNAUTHENTICATED ->
        // HomeScreen.State.Unauthenticated(AuthScreen)
        //     }

        return HomeScreen.State.Authenticated(PlaylistScreen)
    }
}
