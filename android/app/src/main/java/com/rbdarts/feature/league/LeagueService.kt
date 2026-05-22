package com.rbdarts.feature.league

import com.rbdarts.core.data.RBDartsRepository
import com.rbdarts.core.domain.AverageUpdateRule
import com.rbdarts.core.domain.ExtraInningsRule
import com.rbdarts.core.domain.HandicapResult
import com.rbdarts.core.domain.HandicapRoundingRule
import com.rbdarts.core.domain.League
import com.rbdarts.core.domain.ScoringRules
import com.rbdarts.core.domain.TeamStanding
import com.rbdarts.core.domain.TieBonusRule
import java.time.Instant
import java.util.UUID

class LeagueService(private val repository: RBDartsRepository) {
    suspend fun createLeague(
        name: String,
        gamesPerMatch: Int,
        playersPerTeamPerGame: Int,
        handicapPercent: Double
    ): League {
        val now = Instant.now()
        val league = League(
            leagueId = UUID.randomUUID().toString(),
            leagueName = name,
            gamesPerMatch = maxOf(1, gamesPerMatch),
            playersPerTeamPerGame = maxOf(1, playersPerTeamPerGame),
            handicapPercent = handicapPercent.coerceIn(0.0, 100.0),
            handicapRoundingRule = HandicapRoundingRule.Nearest,
            pointsPerGameWin = 1.0,
            matchBonusPointValue = 1.0,
            averageUpdateRule = AverageUpdateRule.AfterMatch,
            extraInningsRule = ExtraInningsRule.Enabled,
            tieBonusRule = TieBonusRule.NoBonus,
            createdAt = now,
            updatedAt = now
        )
        repository.createLeague(league)
        return league
    }

    fun handicapForGame(
        homeTeamId: String,
        awayTeamId: String,
        homeAverageSum: Double,
        awayAverageSum: Double,
        league: League
    ): HandicapResult = ScoringRules.handicap(
        teamAverageSums = mapOf(homeTeamId to homeAverageSum, awayTeamId to awayAverageSum),
        handicapPercent = league.handicapPercent,
        roundingRule = league.handicapRoundingRule
    )

    fun standings(standings: List<TeamStanding>): List<TeamStanding> =
        ScoringRules.rankedStandings(standings)
}
