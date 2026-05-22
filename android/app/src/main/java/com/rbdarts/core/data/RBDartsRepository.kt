package com.rbdarts.core.data

import com.rbdarts.core.domain.CorrectionAuditRecord
import com.rbdarts.core.domain.GameSummary
import com.rbdarts.core.domain.League
import com.rbdarts.core.domain.Match
import com.rbdarts.core.domain.ParticipantScorecard
import com.rbdarts.core.domain.PracticeAttempt
import com.rbdarts.core.domain.StandaloneGame

interface RBDartsRepository {
    suspend fun createStandaloneGame(game: StandaloneGame, participants: List<ParticipantScorecard>)
    suspend fun saveStandaloneSummary(summary: GameSummary, gameId: String)
    suspend fun createLeague(league: League)
    suspend fun createMatch(match: Match)
    suspend fun savePracticeAttempt(attempt: PracticeAttempt)
    suspend fun requestCorrection(correction: CorrectionAuditRecord)
}

class InMemoryRBDartsRepository : RBDartsRepository {
    val standaloneGames = linkedMapOf<String, StandaloneGame>()
    val standaloneSummaries = linkedMapOf<String, GameSummary>()
    val leagues = linkedMapOf<String, League>()
    val matches = linkedMapOf<String, Match>()
    val practiceAttempts = mutableListOf<PracticeAttempt>()
    val corrections = mutableListOf<CorrectionAuditRecord>()

    override suspend fun createStandaloneGame(game: StandaloneGame, participants: List<ParticipantScorecard>) {
        standaloneGames[game.standaloneGameId] = game
    }

    override suspend fun saveStandaloneSummary(summary: GameSummary, gameId: String) {
        standaloneSummaries[gameId] = summary
    }

    override suspend fun createLeague(league: League) {
        leagues[league.leagueId] = league
    }

    override suspend fun createMatch(match: Match) {
        matches[match.matchId] = match
    }

    override suspend fun savePracticeAttempt(attempt: PracticeAttempt) {
        practiceAttempts += attempt
    }

    override suspend fun requestCorrection(correction: CorrectionAuditRecord) {
        corrections += correction
    }
}
