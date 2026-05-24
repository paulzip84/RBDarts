package com.rbdarts.materialyou

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.ui.LoadArtworkAsset
import com.rbdarts.core.ui.LoadArtworkMode
import org.junit.Test

class LoadArtworkAssetContractTest {
    @Test
    fun defaultArtworkUsesLocalOptimizedImage() {
        val asset = LoadArtworkAsset()

        assertThat(asset.sourceResourceName).isEqualTo("rbdarts_load_background")
        assertThat(asset.format).isEqualTo("jpeg")
        assertThat(asset.runtimeDimensions).isEqualTo("806 x 1800")
        assertThat(asset.usesPrimaryImage).isTrue()
    }

    @Test
    fun fallbackArtworkUsesLocalVectorMark() {
        val asset = LoadArtworkAsset(mode = LoadArtworkMode.Fallback)

        assertThat(asset.fallbackResourceName).isEqualTo("rbdarts_loading_mark")
        assertThat(asset.usesPrimaryImage).isFalse()
        assertThat(asset.mode.diagnosticValue).isEqualTo("fallback")
    }

    @Test
    fun missingArtworkStillHasSafeDiagnosticValue() {
        val asset = LoadArtworkAsset(mode = LoadArtworkMode.Missing)

        assertThat(asset.mode.diagnosticValue).isEqualTo("missing")
        assertThat(asset.contentDescription).isEqualTo("RBDarts darts artwork")
    }
}
