// Copyright (C) 2026 Nicholas Doglio
// SPDX-License-Identifier: MIT

package dev.whosnickdoglio.spot.rest.internal

// @ContributesBinding(AppScope::class)
// internal class SpotifyAuthInterceptor(private val tokenProvider: SpotifyTokenProvider) :
//     Interceptor {
//     override fun intercept(chain: Interceptor.Chain): Response {
//         val newRequest =
//             chain
//                 .request()
//                 .newBuilder()
//                 .addHeader("Authorization", "Bearer ${tokenProvider.token()}")
//                 .build()
//
//         return chain.proceed(newRequest)
//     }
// }
