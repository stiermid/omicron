package org.omicron.mobile.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class KtorInstanceApi(
    private val client: HttpClient,
) : InstanceApi {
    override suspend fun getInstance(origin: String): InstanceInfoDto =
        client.get("$origin/api/instance").body()
}
