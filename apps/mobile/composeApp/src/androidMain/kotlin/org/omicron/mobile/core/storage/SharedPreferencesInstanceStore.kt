package org.omicron.mobile.core.storage

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.omicron.mobile.domain.model.InstanceConfiguration

class SharedPreferencesInstanceStore(context: Context) : InstanceStore {
    private val preferences = context.getSharedPreferences("instance", Context.MODE_PRIVATE)

    override suspend fun read(): InstanceConfiguration? =
        withContext(Dispatchers.IO) {
            val origin = preferences.getString(ORIGIN, null) ?: return@withContext null
            InstanceConfiguration(
                origin = origin,
                name = preferences.getString(NAME, null) ?: return@withContext null,
                domain = preferences.getString(DOMAIN, null) ?: return@withContext null,
                federationEnabled = preferences.getBoolean(FEDERATION_ENABLED, false),
                setupComplete = preferences.getBoolean(SETUP_COMPLETE, false),
                emailEnabled = preferences.getBoolean(EMAIL_ENABLED, false),
                emailVerificationRequired = preferences.getBoolean(EMAIL_VERIFICATION_REQUIRED, false),
            )
        }

    override suspend fun write(configuration: InstanceConfiguration) {
        withContext(Dispatchers.IO) {
            preferences
                .edit()
                .putString(ORIGIN, configuration.origin)
                .putString(NAME, configuration.name)
                .putString(DOMAIN, configuration.domain)
                .putBoolean(FEDERATION_ENABLED, configuration.federationEnabled)
                .putBoolean(SETUP_COMPLETE, configuration.setupComplete)
                .putBoolean(EMAIL_ENABLED, configuration.emailEnabled)
                .putBoolean(EMAIL_VERIFICATION_REQUIRED, configuration.emailVerificationRequired)
                .apply()
        }
    }

    private companion object {
        const val ORIGIN = "origin"
        const val NAME = "name"
        const val DOMAIN = "domain"
        const val FEDERATION_ENABLED = "federation_enabled"
        const val SETUP_COMPLETE = "setup_complete"
        const val EMAIL_ENABLED = "email_enabled"
        const val EMAIL_VERIFICATION_REQUIRED = "email_verification_required"
    }
}
