// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package catchup.deeplink

import com.slack.circuit.runtime.screen.Screen

/**
 * Marker interface to indicate that a given [com.slack.circuit.runtime.screen.Screen] can be deep
 * linked to.
 *
 * For object screens, they can simply implement this and return themselves as an instance.
 *
 * ## Configuration
 *
 * ```kotlin
 * @ContributesMultibinding(AppScope::class, binding = binding<DeepLinkable>)
 * @StringKey("home") // The segment/route
 * @Parcelize
 * object HomeScreen : Screen, DeepLinkable {
 *   override fun createScreen(queryParams: ImmutableMap<String, List<String?>): Screen = HomeScreen
 * }
 * ```
 *
 * For class screens (e.g. `data class`), they can define a nested object that implements this and
 * creates the instance to return.
 *
 * ```kotlin
 * @Parcelize
 * data class AboutScreen(val selectedTab: AboutScreenComponent = AboutScreenComponent.DEFAULT) : Screen {
 *   @ContributesMultibinding(AppScope::class)
 *   @StringKey("about")
 *   object DeepLinker : DeepLinkable {
 *     override fun createScreen(queryParams: ImmutableMap<String, List<String?>) =
 *       AboutScreen(AboutScreenComponent.componentFor(queryParams["tab"]))
 *   }
 * }
 * ```
 *
 * ## Contributing to DI
 *
 * To contribute a [DeepLinkable] to DI, use a combination of
 * [dev.zacsweers.metro.ContributesIntoMap] and [dev.zacsweers.metro.StringKey] annotations. The key
 * should be the path segment this screen is associated with.
 *
 * Example:
 * ```kotlin
 * @ContributesIntoMap(AppScope::class, binding = binding<DeepLinkable>)
 * @StringKey("home")
 * @Parcelize
 * object HomeScreen : Screen, DeepLinkable
 * ```
 */
public fun interface DeepLinkable {
    /**
     * Creates a [com.slack.circuit.runtime.screen.Screen] instance for this [DeepLinkable]. The
     * [queryParams] are offered from the original [io.ktor.http.Url] and can be used to customize
     * creation of the screen.
     *
     * Note that the values of the query params are a list of _all_ values for that key found in the
     * url. For example, `https://catchup.zacsweers.dev/home/settings/about/?tab=changelog&tab=foo`
     * would have a `tab` key with values `["changelog", "foo"]`.
     */
    public fun createScreen(queryParams: Map<String, List<String?>>): Screen?
}
