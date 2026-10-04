package org.omicron.mobile.data.api

import kotlinx.serialization.Serializable

interface InstanceApi {
    suspend fun getInstance(origin: String): InstanceInfoDto
}

@Serializable
data class InstanceInfoDto(
    val name: String,
    val domain: String,
    val federationEnabled: Boolean,
    val setupComplete: Boolean,
    val emailEnabled: Boolean,
    val emailVerificationRequired: Boolean,
)
