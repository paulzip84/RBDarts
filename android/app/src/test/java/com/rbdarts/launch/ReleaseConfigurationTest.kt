package com.rbdarts.launch

import com.google.common.truth.Truth.assertThat
import com.rbdarts.core.launch.ReleaseConfiguration
import org.junit.Test

class ReleaseConfigurationTest {
    @Test
    fun defaultProductionConfigurationIsLaunchValid() {
        val configuration = ReleaseConfiguration.defaultProduction()

        configuration.validateForLaunch()

        assertThat(configuration.isProduction).isTrue()
        assertThat(configuration.supportUrl).startsWith("https://")
        assertThat(configuration.privacyPolicyUrl).startsWith("https://")
        assertThat(configuration.accountDeletionUrl).startsWith("https://")
    }

    @Test(expected = IllegalArgumentException::class)
    fun validationRejectsNonProductionEnvironment() {
        ReleaseConfiguration.defaultProduction()
            .copy(appEnvironment = "staging")
            .validateForLaunch()
    }

    @Test(expected = IllegalArgumentException::class)
    fun validationRejectsInsecureUrls() {
        ReleaseConfiguration.defaultProduction()
            .copy(supportUrl = "http://rbdarts.app/support")
            .validateForLaunch()
    }
}
