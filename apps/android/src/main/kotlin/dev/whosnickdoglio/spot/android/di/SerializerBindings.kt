// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.android.di

import androidx.savedstate.serialization.SavedStateConfiguration
import com.slack.circuit.runtime.screen.CircuitSaveable
import com.slack.circuit.runtime.screen.CircuitSaver
import com.slack.circuit.runtime.screen.Screen
import com.slack.circuit.serialization.SerializableCircuitSaver
import dev.whosnickdoglio.spot.auth.AuthScreen
import dev.whosnickdoglio.spot.creation.CreateScreen
import dev.whosnickdoglio.spot.playlists.PlaylistScreen
import dev.whosnickdoglio.spot.settings.SettingsScreen
import dev.whosnickdoglio.spot.url.LaunchUrlScreen
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass

@ContributesTo(AppScope::class)
internal interface SerializerBindings {

    // Workaround https://github.com/slackhq/circuit/issues/2838
    @Provides
    fun provideCircuitSaver(): CircuitSaver =
        SerializableCircuitSaver(
            SavedStateConfiguration {
                serializersModule = SerializersModule {
                    polymorphic(CircuitSaveable::class) {
                        subclass(AuthScreen::class)
                        subclass(SettingsScreen::class)
                        subclass(PlaylistScreen::class)
                        subclass(CreateScreen::class)
                        subclass(LaunchUrlScreen::class)
                    }

                    polymorphic(Screen::class) {
                        subclass(AuthScreen::class)
                        subclass(SettingsScreen::class)
                        subclass(PlaylistScreen::class)
                        subclass(CreateScreen::class)
                        subclass(LaunchUrlScreen::class)
                    }
                }
            }
        )
}
