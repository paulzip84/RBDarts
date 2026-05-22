package com.rbdarts.core.security

import java.time.Instant

data class SessionMetadata(
    val userId: String,
    val providerIds: List<String>,
    val issuedAt: Instant,
    val expiresAt: Instant?
)

interface SecureSessionStore {
    suspend fun save(session: SessionMetadata)
    suspend fun load(): SessionMetadata?
    suspend fun clear()
}

class InMemorySecureSessionStore : SecureSessionStore {
    private var session: SessionMetadata? = null

    override suspend fun save(session: SessionMetadata) {
        this.session = session
    }

    override suspend fun load(): SessionMetadata? = session

    override suspend fun clear() {
        session = null
    }
}
