package com.rbdarts.feature.stats

import com.rbdarts.core.domain.ScoringRules

data class ProjectionResult(
    val label: String,
    val projectedFinalScore: Double
)

class ProjectionService {
    fun baseline(currentScore: Int, inningsPlayed: Int, regulationInnings: Int = 9): ProjectionResult =
        ProjectionResult(
            label = "Estimate",
            projectedFinalScore = ScoringRules.projectedFinalScore(currentScore, inningsPlayed, regulationInnings)
        )
}
