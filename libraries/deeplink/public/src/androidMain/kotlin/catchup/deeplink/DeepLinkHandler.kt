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

import android.content.Intent
import android.net.Uri
import com.slack.circuit.runtime.screen.Screen
import io.ktor.http.Url

/**
 * Parses an [intent] into a list of [Screen]s using [Intent.getData] that can be used as a
 * backstack.
 *
 * Only used if the [Intent.getAction] is [Intent.ACTION_VIEW].
 */
public fun DeepLinkHandler.parse(intent: Intent): List<Screen>? {
    /*
      Example intent:
      adb shell am start \
      -W -a android.intent.action.VIEW \
      -d "catchup://catchup.zacsweers.dev/home/settings/about/?tab=changelog" dev.zacsweers.catchup
    */
    return intent.takeIf { intent.action == Intent.ACTION_VIEW }?.data?.toUrl()?.let(::parse)
}

private fun Uri.toUrl(): Url {
    val uriString = toString()

    // Note HttpUrl doesn't support custom schemes, so we coerce it to https
    val scheme = scheme ?: "https"
    val httpSchemeString = uriString.replaceFirst(scheme, "https")

    return Url(httpSchemeString)
}
