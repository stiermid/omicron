package org.omicron.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import org.omicron.mobile.data.api.KtorInstanceApi
import org.omicron.mobile.data.repository.InstanceRepository
import org.omicron.mobile.core.storage.SharedPreferencesInstanceStore

class MainActivity : ComponentActivity() {
    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(applicationContext)
        setContent { OmicronApp(appContainer.instanceRepository) }
    }

    override fun onDestroy() {
        appContainer.close()
        super.onDestroy()
    }
}

private class AppContainer(context: android.content.Context) {
    private val httpClient = createHttpClient()

    val instanceRepository =
        InstanceRepository(
            api = KtorInstanceApi(httpClient),
            store = SharedPreferencesInstanceStore(context),
        )

    fun close() {
        httpClient.close()
    }
}
