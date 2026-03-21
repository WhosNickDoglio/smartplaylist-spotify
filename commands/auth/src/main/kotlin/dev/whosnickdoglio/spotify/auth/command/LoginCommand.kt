// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spotify.auth.command

import com.github.ajalt.clikt.command.SuspendingCliktCommand
import com.github.ajalt.clikt.core.Context
import com.slack.eithernet.ApiResult
import dev.whosnickdoglio.spotify.auth.command.di.AuthSubcommand
import dev.whosnickdoglio.spotify.auth.rest.SpotifyAccountService
import dev.whosnickdoglio.spotify.rest.di.ClientId
import dev.whosnickdoglio.spotify.rest.di.ClientSecret
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@AuthSubcommand
@ContributesIntoSet(AppScope::class)
public class LoginCommand(
    private val authService: SpotifyAccountService,
    @param:ClientId private val clientId: String,
    @param:ClientSecret private val clientSecret: String,
) : SuspendingCliktCommand("login") {

    override fun help(context: Context): String = "Attempts to login into Spotify"

    override suspend fun run() {
        echo("Attempting to authorize Spotify...")
        when (
            val response =
                authService.requestAccessToken(clientId = clientId, clientSecret = clientSecret)
        ) {
            is ApiResult.Failure -> {
                echo("Request failed!")
            }
            is ApiResult.Success<*> -> {
                echo("Request succeeded! ${response.value}")
            }
        }
    }
}
