/*
 * Copyright (C) 2025 Slack Technologies, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.slack.eithernet.integration.ktor

import com.slack.eithernet.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.HttpClientCall
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.request.HttpResponseData
import io.ktor.client.statement.HttpResponse
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpProtocolVersion
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.util.date.GMTDate
import io.ktor.util.network.UnresolvedAddressException
import io.ktor.utils.io.ByteReadChannel
import io.ktor.utils.io.InternalAPI
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertTrue
import kotlin.time.Clock
import okio.IOException

class KtorEitherNetExtensionsTest {

    @Test
    fun `asKtorApiResult converts ConnectTimeoutException to networkFailure`() {
        val exception = ConnectTimeoutException("Connection timeout")

        val result: ApiResult<Nothing, Any> = exception.asKtorApiResult()

        assertIs<ApiResult.Failure.NetworkFailure>(result)
        assertIs<IOException>(result.error)
        assertEquals(exception, result.error.cause)
    }

    @Test
    fun `asKtorApiResult converts SocketTimeoutException to networkFailure`() {
        val exception = io.ktor.client.network.sockets.SocketTimeoutException("Socket timeout")

        val result: ApiResult<Nothing, Any> = exception.asKtorApiResult()

        assertIs<ApiResult.Failure.NetworkFailure>(result)
        assertIs<IOException>(result.error)
        assertEquals(exception, result.error.cause)
    }

    @Test
    fun `asKtorApiResult converts UnresolvedAddressException to networkFailure`() {
        val exception = io.ktor.util.network.UnresolvedAddressException()

        val result: ApiResult<Nothing, Any> = exception.asKtorApiResult()

        assertIs<ApiResult.Failure.NetworkFailure>(result)
        assertIs<IOException>(result.error)
        assertEquals(exception, result.error.cause)
    }

    @Test
    fun `asKtorApiResult converts unknown exceptions to unknownFailure`() {
        val exception = RuntimeException("Unknown error")

        val result: ApiResult<Nothing, Any> = exception.asKtorApiResult()

        assertIs<ApiResult.Failure.UnknownFailure>(result)
        assertEquals(exception, result.error)
    }

    @Test
    fun `asKtorApiResult preserves exception types for network errors`() {
        val connectTimeout = ConnectTimeoutException("Connect timeout")
        val socketTimeout = io.ktor.client.network.sockets.SocketTimeoutException("Socket timeout")
        val unresolvedAddress = io.ktor.util.network.UnresolvedAddressException()

        val connectResult: ApiResult<Nothing, Any> = connectTimeout.asKtorApiResult()
        val socketResult: ApiResult<Nothing, Any> = socketTimeout.asKtorApiResult()
        val addressResult: ApiResult<Nothing, Any> = unresolvedAddress.asKtorApiResult()

        assertIs<ApiResult.Failure.NetworkFailure>(connectResult)
        assertIs<ApiResult.Failure.NetworkFailure>(socketResult)
        assertIs<ApiResult.Failure.NetworkFailure>(addressResult)

        assertTrue(connectResult.error.cause is ConnectTimeoutException)
        assertTrue(socketResult.error.cause is SocketTimeoutException)
        assertTrue(addressResult.error.cause is UnresolvedAddressException)
    }

    @Test
    fun `asKtorApiResult converts 400 error response with  body to httpFailure`() {
        val response =
            FakeResponse(
                responseData =
                    HttpResponseData(
                        statusCode = HttpStatusCode.BadRequest,
                        requestTime = GMTDate(Clock.System.now().toEpochMilliseconds()),
                        headers = Headers.Empty,
                        version = HttpProtocolVersion.HTTP_2_0,
                        body = mapOf("error" to "oops"),
                        callContext = EmptyCoroutineContext,
                    )
            )

        val result = response.asKtorApiResult<Unit>()

        assertTrue(result is ApiResult.Failure.HttpFailure)
    }

    @Test
    fun `asKtorApiResult converts 500 error response with  body to httpFailure`() {
        val response =
            FakeResponse(
                responseData =
                    HttpResponseData(
                        statusCode = HttpStatusCode.InternalServerError,
                        requestTime = GMTDate(Clock.System.now().toEpochMilliseconds()),
                        headers = Headers.Empty,
                        version = HttpProtocolVersion.HTTP_2_0,
                        body = mapOf("error" to "oops"),
                        callContext = EmptyCoroutineContext,
                    )
            )

        val result = response.asKtorApiResult<Unit>()
        assertTrue(result is ApiResult.Failure.HttpFailure)
    }
}

private class FakeResponse(
    override val call: HttpClientCall =
        HttpClientCall(
            HttpClient(
                engine =
                    MockEngine { _ ->
                        respond(
                            content = ByteReadChannel("""{"ip":"127.0.0.1"}"""),
                            status = HttpStatusCode.OK,
                            headers = headersOf(HttpHeaders.ContentType, "application/json"),
                        )
                    }
            )
        ),
    responseData: HttpResponseData,
) : HttpResponse() {
    override val coroutineContext: CoroutineContext = responseData.callContext
    override val status: HttpStatusCode = responseData.statusCode
    override val version: HttpProtocolVersion = responseData.version
    override val requestTime: GMTDate = responseData.requestTime
    override val responseTime: GMTDate = responseData.responseTime

    @InternalAPI
    override val rawContent: ByteReadChannel =
        responseData.body as? ByteReadChannel ?: ByteReadChannel.Empty

    override val headers: Headers = responseData.headers
}
