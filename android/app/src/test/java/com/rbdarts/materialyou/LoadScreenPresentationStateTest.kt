package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.LaunchPresentationState
import org.junit.Test

class LoadScreenPresentationStateTest {
    @Test
    fun versionLabelCombinesVersionAndBuild() {
        val state = LaunchPresentationState(versionName = "1.2.3", buildNumber = "44")

        assertThat(state.versionLabel).isEqualTo("Version 1.2.3 (44)")
    }

    @Test
    fun versionLabelFallsBackWhenVersionMissing() {
        val state = LaunchPresentationState(versionName = "", buildNumber = "")

        assertThat(state.versionLabel).isEqualTo("Version unavailable")
    }

    @Test
    fun loadingMessageRemovesProviderAndSessionDetails() {
        val state = LaunchPresentationState(
            versionName = "1.2.3",
            buildNumber = "44",
            loadingMessage = "Raw provider payload includes session id"
        )

        assertThat(state.safeLoadingMessage).isEqualTo("Preparing secure darts session")
    }

    @Test
    fun loadingMessageKeepsSafeUserMessage() {
        val state = LaunchPresentationState(
            versionName = "1.2.3",
            buildNumber = "44",
            loadingMessage = "Getting the board ready"
        )

        assertThat(state.safeLoadingMessage).isEqualTo("Getting the board ready")
    }
}
