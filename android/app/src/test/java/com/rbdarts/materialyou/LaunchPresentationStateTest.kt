package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.LaunchPresentationState
import org.junit.Test

class LaunchPresentationStateTest {
    @Test
    fun versionLabelCombinesVersionAndBuild() {
        val state = LaunchPresentationState(versionName = "1.2.3", buildNumber = "44")

        assertThat(state.versionLabel).isEqualTo("Version 1.2.3 (44)")
    }
}
