package org.omicron.mobile.data.repository

import org.omicron.mobile.core.storage.InstanceStore
import org.omicron.mobile.data.api.InstanceApi
import org.omicron.mobile.domain.model.InstanceConfiguration

class InstanceRepository(
    private val api: InstanceApi,
    private val store: InstanceStore,
) {
    suspend fun savedInstance(): InstanceConfiguration? = store.read()

    suspend fun connect(origin: String): InstanceConfiguration {
        val instance = api.getInstance(origin)
        val configuration =
            InstanceConfiguration(
                origin = origin,
                name = instance.name,
                domain = instance.domain,
                federationEnabled = instance.federationEnabled,
                setupComplete = instance.setupComplete,
                emailEnabled = instance.emailEnabled,
                emailVerificationRequired = instance.emailVerificationRequired,
            )
        store.write(configuration)
        return configuration
    }
}
