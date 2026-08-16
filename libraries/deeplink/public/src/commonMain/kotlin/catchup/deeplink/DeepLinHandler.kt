/*
 * Copyright (C) 2026. Zac Sweers
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
