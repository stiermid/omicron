package org.omicron.mobile.core.storage

import org.omicron.mobile.domain.model.InstanceConfiguration

interface InstanceStore {
    suspend fun read(): InstanceConfiguration?

    suspend fun write(configuration: InstanceConfiguration)
}
