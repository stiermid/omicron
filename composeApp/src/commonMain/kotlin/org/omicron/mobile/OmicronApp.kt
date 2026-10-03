package org.omicron.mobile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import org.omicron.mobile.core.designsystem.OmicronTheme
import org.omicron.mobile.core.designsystem.rikkaui.text.Text
import org.omicron.mobile.core.designsystem.rikkaui.text.TextVariant
import org.omicron.mobile.resources.Res
import org.omicron.mobile.resources.app_name
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun OmicronApp() {
    OmicronTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(RikkaTheme.colors.background),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(Res.string.app_name),
                variant = TextVariant.H3,
            )
        }
    }
}
