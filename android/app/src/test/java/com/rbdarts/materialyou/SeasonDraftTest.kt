package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.SeasonDraft
import org.junit.Test

class SeasonDraftTest {
    @Test
    fun requiresNameAndDateWindow() {
        val draft = SeasonDraft(name = "Spring", startsOn = "2026-03-01", endsOn = "2026-05-31")

        assertThat(draft.canSave).isTrue()
    }
}
