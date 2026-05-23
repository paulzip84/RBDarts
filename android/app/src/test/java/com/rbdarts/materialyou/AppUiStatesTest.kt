package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.GameTypeOption
import com.rbdarts.core.ui.BaseballDartsGameType
import org.junit.Test

class AppUiStatesTest {
    @Test
    fun gameTypeOptionsRemainImmutable() {
        val option = GameTypeOption(BaseballDartsGameType.Season, selected = false)

        assertThat(option.copy(selected = true).selected).isTrue()
        assertThat(option.selected).isFalse()
    }
}
