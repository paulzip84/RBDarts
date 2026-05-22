package com.rbdarts.feature.practice

import com.rbdarts.core.domain.PracticeAttempt
import com.rbdarts.core.domain.PracticeStats
import com.rbdarts.core.domain.ScoringRules

class PracticeService {
    fun validate(target: Int, score: Int) {
        ScoringRules.validateInning(target)
        ScoringRules.validateScore(score)
    }

    fun stats(attempts: List<PracticeAttempt>): PracticeStats =
        ScoringRules.practiceStats(attempts)
}
