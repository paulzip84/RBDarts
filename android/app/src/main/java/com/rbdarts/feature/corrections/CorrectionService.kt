package com.rbdarts.feature.corrections

import com.rbdarts.core.domain.CorrectionAuditRecord
import com.rbdarts.core.domain.CorrectionStatus
import com.rbdarts.core.domain.InningScore
import com.rbdarts.core.domain.LeagueRole
import com.rbdarts.core.domain.ScoringRules

class CorrectionService {
    fun requestCorrection(
        score: InningScore,
        newValue: Int,
        reason: String,
        requestedByUserId: String,
        requestedByRole: LeagueRole
    ): CorrectionAuditRecord {
        val (_, audit) = ScoringRules.applyCorrection(
            score = score,
            newValue = newValue,
            reason = reason,
            editedByUserId = requestedByUserId,
            editedByRole = requestedByRole,
            affectedRecords = listOf(score.gameId, score.playerId, "standings")
        )
        return audit.copy(status = CorrectionStatus.Requested, appliedAt = null)
    }
}
