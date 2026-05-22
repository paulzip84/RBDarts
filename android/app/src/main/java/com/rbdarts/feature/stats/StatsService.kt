package com.rbdarts.feature.stats

import com.rbdarts.core.domain.GameSummary
import com.rbdarts.core.domain.ParticipantScorecard
import com.rbdarts.core.domain.ScoringRules
import com.rbdarts.core.domain.TeamStanding

class StatsService {
    fun gameSummary(participants: List<ParticipantScorecard>): GameSummary =
        ScoringRules.summarizeStandalone(participants)

    fun standings(standings: List<TeamStanding>): List<TeamStanding> =
        ScoringRules.rankedStandings(standings)
}
