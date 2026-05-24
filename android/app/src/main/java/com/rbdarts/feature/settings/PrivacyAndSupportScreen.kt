package com.rbdarts.feature.settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import com.rbdarts.core.designsystem.RBDartsAuthenticatedScreen
import com.rbdarts.core.designsystem.RBDartsInfoCard
import com.rbdarts.core.designsystem.RBDartsPrimaryAction
import com.rbdarts.core.designsystem.RBDartsSecondaryAction
import com.rbdarts.core.designsystem.RBDartsSectionHeader
import com.rbdarts.core.launch.ReleaseConfiguration

@Composable
fun PrivacyAndSupportScreen(
    configuration: ReleaseConfiguration,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current

    RBDartsAuthenticatedScreen(modifier = modifier) {
        RBDartsSectionHeader(
            title = "Privacy and Support",
            supportingText = "Review launch details, support resources, privacy policy, and account deletion options."
        )
        RBDartsInfoCard(
            title = "App version",
            body = "Version ${configuration.appVersion} (${configuration.buildNumber})"
        )
        RBDartsPrimaryAction("Support", onClick = { uriHandler.openUri(configuration.supportUrl) })
        RBDartsSecondaryAction("Privacy Policy", onClick = { uriHandler.openUri(configuration.privacyPolicyUrl) })
        RBDartsSecondaryAction("Account Deletion", onClick = { uriHandler.openUri(configuration.accountDeletionUrl) })
    }
}
