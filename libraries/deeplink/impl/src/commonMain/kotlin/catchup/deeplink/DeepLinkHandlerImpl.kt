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
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.ktor.http.Url

@ContributesBinding(AppScope::class)
internal class DeepLinkHandlerImpl(private val routes: Map<String, DeepLinkable>) :
    DeepLinkHandler {
    override fun parse(url: Url): List<Screen> {
        val queryParams =
            url.parameters.names().associateWith { url.parameters.getAll(it) ?: emptyList() }
        return buildList {
            for (segment in url.rawSegments) {

                // Not sure why these are sometimes blank
                if (segment.isBlank()) continue

                // Find a screen and add it
                val screen = screenFor(segment, queryParams)
                if (screen != null) {
                    add(screen)
                } else {
                    // TODO if any segments are null should we just break and return home?
                    // Timber.w("Unknown path segment $segment")
                }
            }
        }
            .toList()
    }

    private fun screenFor(segment: String, queryParams: Map<String, List<String?>>) =
        routes[segment]?.createScreen(queryParams)
}
