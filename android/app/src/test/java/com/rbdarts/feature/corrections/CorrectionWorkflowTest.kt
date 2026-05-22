package com.rbdarts.feature.corrections

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.domain.InningScore
import com.rbdarts.core.domain.LeagueRole
import com.rbdarts.core.domain.CorrectionStatus
import org.junit.Assert.assertThrows
import org.junit.Test
import java.time.Instant

class CorrectionWorkflowTest {
    @Test
    fun correctionCreatesAuditAndRequiresReason() {
        val score = InningScore("score-1", "game-1", "player-1", "team-1", 7, 7, 2, "scorekeeper", Instant.now(), Instant.now())
        val service = CorrectionService()

        assertThrows(IllegalArgumentException::class.java) {
            service.requestCorrection(score, 7, "", "manager", LeagueRole.LeagueManager)
        }

        val audit = service.requestCorrection(score, 7, "Wrong score", "manager", LeagueRole.LeagueManager)
        assertThat(audit.status).isEqualTo(CorrectionStatus.Requested)
        assertThat(audit.previousValue).isEqualTo(2)
        assertThat(audit.newValue).isEqualTo(7)
    }
}
