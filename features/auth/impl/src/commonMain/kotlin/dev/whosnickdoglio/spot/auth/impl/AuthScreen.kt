// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.auth.impl

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.michaelbull.result.onErr
import com.github.michaelbull.result.onOk
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.retained.rememberRetained
import com.slack.circuit.runtime.Navigator
import com.slack.circuit.runtime.internal.rememberStableCoroutineScope
import com.slack.circuit.runtime.presenter.Presenter
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAccessTokenUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.RequestAuthorizationUrlUseCase
import dev.whosnickdoglio.spot.auth.impl.internal.domain.VerifyStateUseCase
import dev.whosnickdoglio.spot.rest.auth.SpotifyTokenProvider
import dev.whosnickdoglio.spot.url.LaunchUrlScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@CircuitInject(AuthScreen::class, AppScope::class)
@Composable
internal fun AuthScreen(state: AuthCircuit.State, modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize(),
    ) {
        if (state.authState == AuthorizationState.UserAuthorized) {
            Text("WOOOO AUTHENTICATED")
        } else {
            Button(
                onClick = { state.eventSink(AuthCircuit.Event.LaunchAuth) },
                modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentSize(),
            ) {
                Text("Authenticate")
            }
        }
    }
}

public sealed interface AuthorizationState {
    public data object Unauthorized : AuthorizationState

    public data object UserAuthorized : AuthorizationState

    public data class Error(val message: String) : AuthorizationState
}

@AssistedInject
internal class AuthPresenter(
    @Assisted private val screen: AuthScreen,
    @Assisted private val navigator: Navigator,
    private val requestAuthorizationUrlUseCase: RequestAuthorizationUrlUseCase,
    private val verifyStateUseCase: VerifyStateUseCase,
    private val requestAccessTokenUseCase: RequestAccessTokenUseCase,
    private val tokenRepository: SpotifyTokenProvider,
) : Presenter<AuthCircuit.State> {

    @CircuitInject(AuthScreen::class, AppScope::class)
    @AssistedFactory
    fun interface Factory {
        fun create(screen: AuthScreen, navigator: Navigator): AuthPresenter
    }

    @Composable
    override fun present(): AuthCircuit.State {
        val scope = rememberStableCoroutineScope()
        var authState: AuthorizationState by rememberRetained {
            mutableStateOf(AuthorizationState.Unauthorized)
        }

        var errorMessage: String? by remember { mutableStateOf(null) }

        if (screen.code != null && screen.state != null) {
            authState = AuthorizationState.UserAuthorized
            LaunchedEffect(screen) {
                val isSameStateValue = verifyStateUseCase(screen.state!!)
            }
        }

        LaunchedEffect(authState) {
            when (authState) {
                is AuthorizationState.Error -> {
                    errorMessage = (authState as AuthorizationState.Error).message
                }
                is AuthorizationState.Unauthorized -> Unit
                is AuthorizationState.UserAuthorized -> {
                    if (screen.code != null) {
                        val tokens = requestAccessTokenUseCase(screen.code!!)
                        tokens
                            .onOk {
                                tokenRepository.putTokens(it)
                                if (screen.bounceBack != null) {
                                    navigator.goTo(screen.bounceBack!!)
                                }
                            }
                            .onErr {
                                // TODO
                            }
                    }
                }
            }
        }

        return AuthCircuit.State(authState = authState, errorMessage = errorMessage) { event ->
            when (event) {
                AuthCircuit.Event.LaunchAuth -> {
                    scope.launch {
                        val url = requestAuthorizationUrlUseCase()

                        navigator.goTo(LaunchUrlScreen(url))
                    }
                }
            }
        }
    }
}
