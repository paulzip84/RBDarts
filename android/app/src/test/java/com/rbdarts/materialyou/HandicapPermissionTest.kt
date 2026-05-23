package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.PlayerHandicapState
import com.rbdarts.feature.handicap.canEditHandicap
import org.junit.Test

class HandicapPermissionTest {
    @Test
    fun permissionHelperReflectsEditableState() {
        assertThat(canEditHandicap(PlayerHandicapState("Guest", 30, canEdit = false))).isFalse()
    }
}
