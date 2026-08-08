// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package catchup.deeplink

import com.slack.circuit.runtime.screen.Screen
import io.ktor.http.Url

/**
 * A simple handler for deep links.
 *
 * @see parse for primary documentation.
 */
public fun interface DeepLinkHandler {
    /**
     * Parses an [Url] into a list of [Screen]s that can be used as a backstack.
     *
     * Guaranteed to always have at least one element, returns null if the url is invalid.
     *
     * ## Example
     *
     * The given url `https://catchup.zacsweers.dev/home/settings/about/?tab=changelog` would
     * resolve to a list of [Screen]s like:
     * - `HomeScreen`
     * - `SettingsScreen`
     * - `AboutScreen` where its default tab is `ChangelogScreen`
     */
    public fun parse(url: Url): List<Screen>?
}
