package com.rbdarts.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.rbdarts.core.launch.ReleaseConfiguration

@Composable
fun PrivacyAndSupportScreen(
    configuration: ReleaseConfiguration,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text("Privacy and Support", style = MaterialTheme.typography.titleMedium)
        Text("Version ${configuration.appVersion} (${configuration.buildNumber})")
        Button(onClick = { uriHandler.openUri(configuration.supportUrl) }) {
            Text("Support")
        }
        Button(onClick = { uriHandler.openUri(configuration.privacyPolicyUrl) }) {
            Text("Privacy Policy")
        }
        Button(onClick = { uriHandler.openUri(configuration.accountDeletionUrl) }) {
            Text("Account Deletion")
        }
    }
}
