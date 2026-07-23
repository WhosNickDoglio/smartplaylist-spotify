// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

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
