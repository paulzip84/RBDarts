package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.feature.scoring.isValidScoreInput
import org.junit.Test

class ScoringValidationUiTest {
    @Test
    fun scoreInputRejectsOutOfRangeValues() {
        assertThat(isValidScoreInput("9")).isTrue()
        assertThat(isValidScoreInput("10")).isFalse()
        assertThat(isValidScoreInput("abc")).isFalse()
    }
}
