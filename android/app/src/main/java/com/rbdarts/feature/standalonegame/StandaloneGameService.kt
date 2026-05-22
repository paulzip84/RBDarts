package com.rbdarts.feature.standalonegame

import com.rbdarts.core.data.RBDartsRepository
import com.rbdarts.core.domain.GameStatus
import com.rbdarts.core.domain.GameSummary
import com.rbdarts.core.domain.ParticipantScorecard
import com.rbdarts.core.domain.ParticipantType
import com.rbdarts.core.domain.ScoringRules
import com.rbdarts.core.domain.StandaloneGame
import java.time.Instant
import java.util.UUID

data class ActiveScoreSession(
    val sessionId: String,
    val updatedAt: Instant,
    val game: StandaloneGame,
    val participants: List<ParticipantScorecard>,
    val pendingSyncRequestIds: List<String> = emptyList()
)

interface ActiveScoreSessionStore {
    suspend fun save(session: ActiveScoreSession)
    suspend fun loadMostRecent(): ActiveScoreSession?
    suspend fun delete(sessionId: String)
}

class InMemoryActiveScoreSessionStore : ActiveScoreSessionStore {
    private var latest: ActiveScoreSession? = null

    override suspend fun save(session: ActiveScoreSession) {
        latest = session
    }

    override suspend fun loadMostRecent(): ActiveScoreSession? = latest

    override suspend fun delete(sessionId: String) {
        if (latest?.sessionId == sessionId) latest = null
    }
}

class StandaloneGameService(
    private val repository: RBDartsRepository,
    private val sessionStore: ActiveScoreSessionStore = InMemoryActiveScoreSessionStore()
) {
    suspend fun createGame(
        name: String,
        createdByUserId: String,
        participantNames: List<String>,
        regulationInnings: Int = ScoringRules.DefaultRegulationInnings,
        extraInningsEnabled: Boolean = true
    ): ActiveScoreSession {
        val now = Instant.now()
        val gameId = UUID.randomUUID().toString()
        val game = StandaloneGame(
            standaloneGameId = gameId,
            createdByUserId = createdByUserId,
            gameName = name.trim().ifBlank { "Standalone Game" },
            gameDate = now,
            participantType = ParticipantType.Singles,
            regulationInnings = regulationInnings,
            extraInningsEnabled = extraInningsEnabled,
            status = GameStatus.Scoring,
            createdAt = now,
            completedAt = null
        )
        val participants = participantNames
            .filter { it.isNotBlank() }
            .mapIndexed { index, participantName ->
                ParticipantScorecard(
                    participantId = "participant-${index + 1}",
                    displayName = participantName.trim()
                )
            }
            .ifEmpty {
                listOf(ParticipantScorecard(participantId = "participant-1", displayName = "Player 1"))
            }

        val session = ActiveScoreSession(
            sessionId = gameId,
            updatedAt = now,
            game = game,
            participants = participants
        )
        sessionStore.save(session)
        repository.createStandaloneGame(game, participants)
        return session
    }

    suspend fun enterScore(
        score: Int,
        participantId: String,
        inningNumber: Int,
        session: ActiveScoreSession
    ): ActiveScoreSession {
        val updatedParticipants = session.participants.map { participant ->
            if (participant.participantId == participantId) {
                ScoringRules.recordScore(participant, inningNumber, score)
            } else {
                participant
            }
        }
        val updated = session.copy(updatedAt = Instant.now(), participants = updatedParticipants)
        sessionStore.save(updated)
        return updated
    }

    suspend fun undoScore(
        participantId: String,
        inningNumber: Int,
        session: ActiveScoreSession
    ): ActiveScoreSession {
        val updatedParticipants = session.participants.map { participant ->
            if (participant.participantId == participantId) {
                participant.copy(scoresByInning = participant.scoresByInning - inningNumber)
            } else {
                participant
            }
        }
        val updated = session.copy(updatedAt = Instant.now(), participants = updatedParticipants)
        sessionStore.save(updated)
        return updated
    }

    suspend fun complete(session: ActiveScoreSession): GameSummary {
        val summary = ScoringRules.summarizeStandalone(
            participants = session.participants,
            regulationInnings = session.game.regulationInnings,
            extraInningsEnabled = session.game.extraInningsEnabled
        )
        repository.saveStandaloneSummary(summary, session.game.standaloneGameId)
        return summary
    }

    suspend fun recoverMostRecentSession(): ActiveScoreSession? = sessionStore.loadMostRecent()
}
