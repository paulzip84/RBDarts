package com.rbdarts.feature.auth

import java.time.Instant

enum class AuthProviderId { Google, Facebook, Apple }

data class AuthSession(
    val userId: String,
    val displayName: String,
    val providerId: AuthProviderId,
    val expiresAt: Instant?
)

sealed class AuthProviderException(message: String) : Exception(message) {
    data object Cancelled : AuthProviderException("Sign-in was cancelled.")
    data object ProviderUnavailable : AuthProviderException("The requested provider is unavailable.")
    data object TokenExchangeFailed : AuthProviderException("Unable to exchange provider token.")
}

interface AuthProvider {
    val providerId: AuthProviderId
    suspend fun signIn(): AuthSession
    suspend fun signOut()
}

class AuthProviderRegistry(providers: List<AuthProvider>) {
    private val providersById = providers.associateBy { it.providerId }

    fun provider(providerId: AuthProviderId): AuthProvider =
        providersById[providerId] ?: throw AuthProviderException.ProviderUnavailable
}
