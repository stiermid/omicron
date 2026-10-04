package org.omicron.mobile.domain.model

data class InstanceConfiguration(
    val origin: String,
    val name: String,
    val domain: String,
    val federationEnabled: Boolean,
    val setupComplete: Boolean,
    val emailEnabled: Boolean,
    val emailVerificationRequired: Boolean,
)
