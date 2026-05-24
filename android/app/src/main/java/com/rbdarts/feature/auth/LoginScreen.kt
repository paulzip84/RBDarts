package com.rbdarts.feature.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import com.rbdarts.core.designsystem.LoginBrandHeader
import com.rbdarts.core.designsystem.LoginMessageBanner
import com.rbdarts.core.designsystem.LoginProviderButton
import com.rbdarts.core.designsystem.LoginSsoDivider
import com.rbdarts.core.designsystem.LoginSupportLinks
import com.rbdarts.core.designsystem.RBDartsPremiumLoginSurface
import com.rbdarts.core.launch.ReleaseConfiguration
import com.rbdarts.core.ui.AccountSupportKind
import com.rbdarts.core.ui.AccountSupportLink
import com.rbdarts.core.ui.AuthUiState
import com.rbdarts.core.ui.LinkProminence
import com.rbdarts.core.ui.LoginPresentationState
import com.rbdarts.core.ui.SsoProviderId

@Composable
fun LoginScreen(
    state: AuthUiState,
    configuration: ReleaseConfiguration,
    onGoogleSignIn: () -> Unit,
    onFacebookSignIn: () -> Unit,
    modifier: Modifier = Modifier
) {
    val uriHandler = LocalUriHandler.current
    val presentation = LoginPresentationState.fromAuthState(
        authState = state,
        supportLinks = configuration.loginSupportLinks()
    )

    RBDartsPremiumLoginSurface(modifier = modifier) {
        Spacer(Modifier.height(12.dp))
        LoginBrandHeader(
            brandName = presentation.brandName,
            headline = presentation.headline,
            supportingText = presentation.supportingText,
            logoContentDescription = presentation.logoContentDescription
        )
        presentation.message?.let { message ->
            LoginMessageBanner(message = message)
        }
        LoginSsoDivider(label = "Secure SSO")
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            presentation.providerActions.forEach { action ->
                LoginProviderButton(
                    action = action,
                    onClick = {
                        when (action.providerId) {
                            SsoProviderId.Google -> onGoogleSignIn()
                            SsoProviderId.Facebook -> onFacebookSignIn()
                        }
                    }
                )
            }
        }
        LoginSupportLinks(
            links = presentation.supportLinks,
            onOpenLink = { link -> uriHandler.openUri(link.url) }
        )
    }
}

private fun ReleaseConfiguration.loginSupportLinks(): List<AccountSupportLink> =
    listOf(
        AccountSupportLink(
            kind = AccountSupportKind.SignInHelp,
            label = "Need help signing in?",
            url = supportUrl,
            prominence = LinkProminence.Inline
        ),
        AccountSupportLink(
            kind = AccountSupportKind.Support,
            label = "Support",
            url = supportUrl
        ),
        AccountSupportLink(
            kind = AccountSupportKind.PrivacyPolicy,
            label = "Privacy Policy",
            url = privacyPolicyUrl
        ),
        AccountSupportLink(
            kind = AccountSupportKind.AccountDeletion,
            label = "Account Deletion",
            url = accountDeletionUrl
        )
    )
