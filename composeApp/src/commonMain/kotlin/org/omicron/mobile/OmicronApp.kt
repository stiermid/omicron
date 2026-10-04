package org.omicron.mobile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import org.omicron.mobile.core.designsystem.OmicronTheme
import org.omicron.mobile.data.repository.InstanceRepository
import org.omicron.mobile.feature.connect.ConnectRoute
import org.omicron.mobile.feature.connect.ConnectViewModel

@Composable
fun OmicronApp(instanceRepository: InstanceRepository) {
    OmicronTheme {
        val viewModel = remember(instanceRepository) { ConnectViewModel(instanceRepository) }
        DisposableEffect(viewModel) {
            onDispose(viewModel::close)
        }
        ConnectRoute(viewModel)
    }
}
