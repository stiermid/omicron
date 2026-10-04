package org.omicron.mobile.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals

class KtorInstanceApiTest {
    @Test
    fun readsPublicInstanceMetadata() = runTest {
        val client =
            HttpClient(
                MockEngine {
                    respond(
                        content =
                            """{"name":"Omicron","domain":"omicron.blog","federationEnabled":true,"setupComplete":true,"emailEnabled":true,"emailVerificationRequired":true}""",
                        status = HttpStatusCode.OK,
                        headers = headersOf(HttpHeaders.ContentType, "application/json"),
                    )
                },
            ) {
                install(ContentNegotiation) { json(Json { ignoreUnknownKeys = true }) }
            }

        val instance = KtorInstanceApi(client).getInstance("https://omicron.blog")

        assertEquals("Omicron", instance.name)
        assertEquals("omicron.blog", instance.domain)
        assertEquals(true, instance.federationEnabled)
    }
}
