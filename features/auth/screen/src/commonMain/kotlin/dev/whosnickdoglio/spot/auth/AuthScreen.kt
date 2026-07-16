// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.runtime.CircuitUiState
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import com.slack.circuit.runtime.screen.Screen
import dev.whosnickdoglio.spot.auth.internal.CodeChallenge
import dev.whosnickdoglio.spot.rest.SpotifyAccountService
import dev.whosnickdoglio.spot.url.LaunchUrlScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import io.github.solcott.kmp.parcelize.Parcelize
import kotlin.uuid.Uuid
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
public data object AuthScreen : Screen {
    public data class State(
        val isAuthenticated: Boolean,
        val errorMessage: String?,
        val eventSink: (Event) -> Unit,
    ) : CircuitUiState

    public sealed interface Event {
        public data object LaunchAuth : Event
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@CircuitInject(AuthScreen::class, AppScope::class)
@Composable
internal fun AuthScreen(state: AuthScreen.State, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        Button(
            onClick = { state.eventSink(AuthScreen.Event.LaunchAuth) },
            modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentSize(),
        ) {
            Text("Authenticate")
        }
    }
}

@AssistedInject
internal class AuthPresenter(
    @Assisted private val navigator: Navigator,
    private val spotifyAccountService: SpotifyAccountService,
    private val codeChallenge: CodeChallenge,
) : Presenter<AuthScreen.State> {

    private val state by lazy { Uuid.random().toString() }

    @CircuitInject(AuthScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(@Assisted navigator: Navigator): AuthPresenter
    }

    @Composable
    override fun present(): AuthScreen.State {
        val scope = rememberStableCoroutineScope()
        return AuthScreen.State(isAuthenticated = false, errorMessage = null) { event ->
            when (event) {
                AuthScreen.Event.LaunchAuth -> {
                    scope.launch {
                        val challenge = codeChallenge.challenge()
                        val url =
                            spotifyAccountService.getAuthUrl(
                                state = state,
                                codeChallenge = challenge,
                            )

                        navigator.goTo(LaunchUrlScreen(url.toString()))
                    }
                }
            }
        }
    }
}
