package com.rbdarts.feature.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rbdarts.core.ui.AuthFlowStatus
import com.rbdarts.core.ui.AuthUiState
import java.time.Instant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authProviderRegistry: AuthProviderRegistry = AuthProviderRegistry(
        listOf(
            LocalSsoAuthProvider(AuthProviderId.Google),
            LocalSsoAuthProvider(AuthProviderId.Facebook)
        )
    )
) : ViewModel() {
    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()

    fun signIn(providerId: AuthProviderId) {
        if (_uiState.value.isLoading) return
        _uiState.value = AuthUiState(status = AuthFlowStatus.Loading, selectedProvider = providerId.name)
        viewModelScope.launch {
            runCatching { authProviderRegistry.provider(providerId).signIn() }
                .onSuccess { session ->
                    _uiState.value = AuthUiState(
                        status = AuthFlowStatus.Authenticated,
                        selectedProvider = providerId.name,
                        displayName = session.displayName
                    )
                }
                .onFailure { error ->
                    _uiState.value = when (error) {
                        AuthProviderException.Cancelled -> cancelledState(providerId)
                        AuthProviderException.ProviderUnavailable -> unavailableState(providerId)
                        else -> failedState(providerId)
                    }
                }
        }
    }

    fun cancel(providerId: AuthProviderId) {
        _uiState.value = cancelledState(providerId)
    }

    fun fail(providerId: AuthProviderId) {
        _uiState.value = failedState(providerId)
    }

    fun providerUnavailable(providerId: AuthProviderId) {
        _uiState.value = unavailableState(providerId)
    }

    fun offline() {
        _uiState.value = AuthUiState(
            status = AuthFlowStatus.Offline,
            errorMessage = "SSO requires an internet connection. Reconnect and try again."
        )
    }

    fun sessionExpired() {
        _uiState.value = AuthUiState(
            status = AuthFlowStatus.SessionExpired,
            errorMessage = "Please sign in again to continue."
        )
    }

    fun signOut() {
        viewModelScope.launch {
            runCatching { authProviderRegistry.provider(AuthProviderId.Google).signOut() }
            runCatching { authProviderRegistry.provider(AuthProviderId.Facebook).signOut() }
            _uiState.value = AuthUiState()
        }
    }

    private fun cancelledState(providerId: AuthProviderId): AuthUiState =
        AuthUiState(
            status = AuthFlowStatus.Cancelled,
            selectedProvider = providerId.name,
            errorMessage = "${providerId.name} sign-in was cancelled."
        )

    private fun failedState(providerId: AuthProviderId): AuthUiState =
        AuthUiState(
            status = AuthFlowStatus.Failed,
            selectedProvider = providerId.name,
            errorMessage = "Unable to sign in with ${providerId.name}. Please try again."
        )

    private fun unavailableState(providerId: AuthProviderId): AuthUiState =
        AuthUiState(
            status = AuthFlowStatus.Unavailable,
            selectedProvider = providerId.name,
            errorMessage = "${providerId.name} sign-in is temporarily unavailable."
        )
}

class LocalSsoAuthProvider(
    override val providerId: AuthProviderId,
    private val available: Boolean = true
) : AuthProvider {
    override suspend fun signIn(): AuthSession {
        if (!available) throw AuthProviderException.ProviderUnavailable
        return AuthSession(
            userId = "local-${providerId.name.lowercase()}-session",
            displayName = "RBDarts Player",
            providerId = providerId,
            expiresAt = Instant.now().plusSeconds(3600)
        )
    }

    override suspend fun signOut() = Unit
}
