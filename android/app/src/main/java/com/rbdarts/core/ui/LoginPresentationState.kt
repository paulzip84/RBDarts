package com.rbdarts.core.ui

enum class LoginVisualStyle { DarkPremium, LightFallback, DynamicColor }

enum class SsoProviderId(
    val authProviderName: String,
    val displayName: String,
    val defaultLabel: String
) {
    Google("Google", "Google", "Continue with Google"),
    Facebook("Facebook", "Facebook", "Continue with Facebook")
}

enum class ProviderBrandAssetState { Available, MissingFallback, Unavailable }

enum class ProviderAvailability { Available, Unavailable, RequiresNetwork, NotConfigured }

enum class ProviderInteractionState { Idle, Loading, Succeeded, Cancelled, Failed }

enum class LoginMessageType { Info, Warning, Error, Success }

enum class AccountSupportKind { SignInHelp, Support, PrivacyPolicy, AccountDeletion }

enum class LinkProminence { Inline, Secondary, Overflow }

data class LoginMessage(
    val type: LoginMessageType,
    val title: String,
    val body: String,
    val actionLabel: String? = null,
    val reasonCode: String
)

data class AccountSupportLink(
    val kind: AccountSupportKind,
    val label: String,
    val url: String,
    val prominence: LinkProminence = LinkProminence.Secondary
)

data class SsoProviderAction(
    val providerId: SsoProviderId,
    val label: String = providerId.defaultLabel,
    val brandAssetState: ProviderBrandAssetState = ProviderBrandAssetState.Available,
    val availability: ProviderAvailability = ProviderAvailability.Available,
    val interactionState: ProviderInteractionState = ProviderInteractionState.Idle,
    val accessibilityLabel: String = "${providerId.defaultLabel} using ${providerId.displayName} SSO",
    val retryAllowed: Boolean = true,
    val enabled: Boolean = true
) {
    val isLoading: Boolean = interactionState == ProviderInteractionState.Loading
    val canClick: Boolean = enabled && availability == ProviderAvailability.Available && !isLoading
}

data class LoginPresentationState(
    val brandName: String = "RBDarts",
    val headline: String = "Welcome back",
    val supportingText: String = "Choose a trusted provider to continue to RBDarts.",
    val visualStyle: LoginVisualStyle = LoginVisualStyle.DarkPremium,
    val logoContentDescription: String = "RBDarts mark",
    val providerActions: List<SsoProviderAction> = defaultProviderActions(),
    val supportLinks: List<AccountSupportLink> = emptyList(),
    val message: LoginMessage? = null,
    val preservedRouteIntent: String? = null
) {
    val visibleText: List<String> =
        listOfNotNull(brandName, headline, supportingText, message?.title, message?.body) +
            providerActions.map { it.label } +
            supportLinks.map { it.label }

    val hasFirstPartyCredentialCopy: Boolean =
        visibleText.any { text ->
            val normalized = text.lowercase()
            normalized.contains("password") || normalized.contains("email") || normalized.contains("forgot")
        }

    companion object {
        fun fromAuthState(
            authState: AuthUiState,
            supportLinks: List<AccountSupportLink> = emptyList()
        ): LoginPresentationState = LoginPresentationState(
            providerActions = defaultProviderActions(authState),
            supportLinks = supportLinks,
            message = authState.safeLoginMessage()
        )
    }
}

fun defaultProviderActions(authState: AuthUiState = AuthUiState()): List<SsoProviderAction> {
    val selectedProvider = authState.selectedProvider
    val anyProviderLoading = authState.status == AuthFlowStatus.Loading
    return SsoProviderId.entries.map { provider ->
        val selected = selectedProvider == provider.authProviderName
        val interactionState = when {
            selected && authState.status == AuthFlowStatus.Loading -> ProviderInteractionState.Loading
            selected && authState.status == AuthFlowStatus.Cancelled -> ProviderInteractionState.Cancelled
            selected && authState.status in listOf(AuthFlowStatus.Failed, AuthFlowStatus.Unavailable, AuthFlowStatus.Offline) ->
                ProviderInteractionState.Failed
            selected && authState.status == AuthFlowStatus.Authenticated -> ProviderInteractionState.Succeeded
            else -> ProviderInteractionState.Idle
        }
        val availability = when {
            authState.status == AuthFlowStatus.Offline -> ProviderAvailability.RequiresNetwork
            selected && authState.status == AuthFlowStatus.Unavailable -> ProviderAvailability.Unavailable
            else -> ProviderAvailability.Available
        }
        SsoProviderAction(
            providerId = provider,
            label = if (interactionState == ProviderInteractionState.Loading) {
                "Connecting to ${provider.displayName}"
            } else {
                provider.defaultLabel
            },
            availability = availability,
            interactionState = interactionState,
            accessibilityLabel = if (interactionState == ProviderInteractionState.Loading) {
                "Connecting to ${provider.displayName}"
            } else {
                "${provider.defaultLabel} using ${provider.displayName} SSO"
            },
            retryAllowed = interactionState != ProviderInteractionState.Loading,
            enabled = !anyProviderLoading && availability == ProviderAvailability.Available
        )
    }
}

fun AuthUiState.safeLoginMessage(): LoginMessage? =
    when (status) {
        AuthFlowStatus.Cancelled -> LoginMessage(
            type = LoginMessageType.Warning,
            title = "Sign-in cancelled",
            body = errorMessage ?: "${selectedProvider.orEmpty().ifBlank { "Provider" }} sign-in was cancelled.",
            actionLabel = "Try again",
            reasonCode = "provider_cancelled"
        )
        AuthFlowStatus.Failed -> LoginMessage(
            type = LoginMessageType.Error,
            title = "Sign-in needs attention",
            body = errorMessage ?: "Provider sign-in could not complete. Please try again.",
            actionLabel = "Try again",
            reasonCode = "provider_failed"
        )
        AuthFlowStatus.Unavailable -> LoginMessage(
            type = LoginMessageType.Warning,
            title = "Provider unavailable",
            body = errorMessage ?: "This sign-in provider is temporarily unavailable.",
            actionLabel = "Try again",
            reasonCode = "provider_unavailable"
        )
        AuthFlowStatus.Offline -> LoginMessage(
            type = LoginMessageType.Warning,
            title = "Connection required",
            body = errorMessage ?: "SSO requires an internet connection. Reconnect and try again.",
            actionLabel = "Retry",
            reasonCode = "offline"
        )
        AuthFlowStatus.SessionExpired -> LoginMessage(
            type = LoginMessageType.Info,
            title = "Session expired",
            body = errorMessage ?: "Please sign in again to continue.",
            actionLabel = "Sign in again",
            reasonCode = "session_expired"
        )
        AuthFlowStatus.Idle, AuthFlowStatus.Loading, AuthFlowStatus.Authenticated -> null
    }
