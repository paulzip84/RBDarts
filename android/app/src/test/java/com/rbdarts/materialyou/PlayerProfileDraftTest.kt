package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.PlayerProfileDraft
import org.junit.Test

class PlayerProfileDraftTest {
    @Test
    fun requiresAUsableDisplayName() {
        assertThat(PlayerProfileDraft(displayName = "A").canSave).isFalse()
        assertThat(PlayerProfileDraft(displayName = "Avery", seedAverageText = "42").canSave).isTrue()
    }
}
