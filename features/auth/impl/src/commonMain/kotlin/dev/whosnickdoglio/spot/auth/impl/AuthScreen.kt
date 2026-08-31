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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.github.michaelbull.result.get
import com.github.michaelbull.result.getErrorOr
import com.github.michaelbull.result.mapError
import com.github.michaelbull.result.onOk
import com.slack.circuit.codegen.annotations.CircuitInject
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
import kotlinx.coroutines.CoroutineScope
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
        when (state) {
            is AuthCircuit.State.Error -> {
                Button(
                    onClick = { state.eventSink(AuthCircuit.Event.LaunchAuth) },
                    modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentSize(),
                ) {
                    Text("Authenticate")
                }
            }

            is AuthCircuit.State.Unauthorized -> {
                Button(
                    onClick = { state.eventSink(AuthCircuit.Event.LaunchAuth) },
                    modifier = Modifier.align(Alignment.CenterHorizontally).wrapContentSize(),
                ) {
                    Text("Authenticate")
                }
            }
        }
    }
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
        val state by
            produceState<AuthCircuit.State>(
                initialValue =
                    AuthCircuit.State.Unauthorized({
                        eventSink(
                            it,
                            scope,
                        )
                    }),
                key1 = screen,
            ) {
                if (screen.code != null) {
                    val isSameStateValue = verifyStateUseCase(screen.state!!)
                    if (isSameStateValue) {
                        value =
                            AuthCircuit.State.Error(
                                "oops",
                                {
                                    eventSink(
                                        it,
                                        scope,
                                    )
                                },
                            )
                    }
                    val tokens =
                        requestAccessTokenUseCase(screen.code!!)
                            .onOk {
                                tokenRepository.putTokens(it)
                                navigator.pop()
                            }
                            .mapError {
                                AuthCircuit.State.Error(
                                    "oops",
                                    {
                                        eventSink(
                                            it,
                                            scope,
                                        )
                                    },
                                )
                            }

                    value =
                        tokens.getErrorOr(
                            AuthCircuit.State.Error("") {
                                eventSink(
                                    it,
                                    scope,
                                )
                            }
                        )
                }
            }

        return state
    }

    private fun eventSink(event: AuthCircuit.Event, scope: CoroutineScope) {
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
