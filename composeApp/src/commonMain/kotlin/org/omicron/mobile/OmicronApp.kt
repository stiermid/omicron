package org.omicron.mobile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.omicron.mobile.core.designsystem.OmicronTheme
import org.omicron.mobile.core.designsystem.rikkaui.text.Text
import org.omicron.mobile.core.designsystem.rikkaui.text.TextVariant
import org.omicron.mobile.resources.Res
import org.omicron.mobile.resources.app_name
import org.omicron.mobile.resources.omicron_logo
import zed.rainxch.rikkaui.foundation.RikkaTheme

@Composable
fun OmicronApp() {
    OmicronTheme {
        Box(
            modifier = Modifier.fillMaxSize().background(RikkaTheme.colors.background),
            contentAlignment = Alignment.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Image(
                    painter = painterResource(Res.drawable.omicron_logo),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp),
                )
                Text(
                    text = stringResource(Res.string.app_name),
                    variant = TextVariant.H3,
                )
            }
        }
    }
}
