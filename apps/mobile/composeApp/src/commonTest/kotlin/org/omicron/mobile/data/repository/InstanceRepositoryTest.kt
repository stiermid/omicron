package org.omicron.mobile.data.repository

import kotlinx.coroutines.test.runTest
import org.omicron.mobile.core.storage.InstanceStore
import org.omicron.mobile.data.api.InstanceApi
import org.omicron.mobile.data.api.InstanceInfoDto
import org.omicron.mobile.domain.model.InstanceConfiguration
import kotlin.test.Test
import kotlin.test.assertEquals

class InstanceRepositoryTest {
    @Test
    fun storesTheConfigurationReturnedByTheInstance() = runTest {
        val store = FakeInstanceStore()
        val repository = InstanceRepository(FakeInstanceApi(), store)

        val configuration = repository.connect("https://omicron.blog")

        assertEquals("https://omicron.blog", configuration.origin)
        assertEquals(configuration, store.configuration)
    }
}

private class FakeInstanceApi : InstanceApi {
    override suspend fun getInstance(origin: String): InstanceInfoDto =
        InstanceInfoDto(
            name = "Omicron",
            domain = "omicron.blog",
            federationEnabled = true,
            setupComplete = true,
            emailEnabled = true,
            emailVerificationRequired = true,
        )
}

private class FakeInstanceStore : InstanceStore {
    var configuration: InstanceConfiguration? = null

    override suspend fun read(): InstanceConfiguration? = configuration

    override suspend fun write(configuration: InstanceConfiguration) {
        this.configuration = configuration
    }
}
