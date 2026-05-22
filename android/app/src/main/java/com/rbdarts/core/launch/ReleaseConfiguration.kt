package com.rbdarts.core.launch

import android.content.Context
import java.net.URI

data class ReleaseConfiguration(
    val appEnvironment: String,
    val appVersion: String,
    val buildNumber: String,
    val supportUrl: String,
    val privacyPolicyUrl: String,
    val accountDeletionUrl: String,
    val diagnosticsEnabled: Boolean
) {
    val isProduction: Boolean = appEnvironment == "production"

    fun validateForLaunch() {
        require(isProduction) { "Launch candidates must use production configuration." }
        listOf(supportUrl, privacyPolicyUrl, accountDeletionUrl).forEach { value ->
            val uri = URI(value)
            require(uri.scheme == "https" && uri.host?.isNotBlank() == true) {
                "Launch URL must be https: $value"
            }
        }
    }

    companion object {
        fun defaultProduction(): ReleaseConfiguration = ReleaseConfiguration(
            appEnvironment = "production",
            appVersion = "0.1.0",
            buildNumber = "1",
            supportUrl = "https://rbdarts.app/support",
            privacyPolicyUrl = "https://rbdarts.app/privacy",
            accountDeletionUrl = "https://rbdarts.app/account/delete",
            diagnosticsEnabled = true
        )

        fun fromContext(context: Context): ReleaseConfiguration {
            val resources = context.resources
            val packageName = context.packageName
            return ReleaseConfiguration(
                appEnvironment = resources.stringValue(packageName, "release_app_environment", "production"),
                appVersion = resources.stringValue(packageName, "release_app_version", "0.1.0"),
                buildNumber = resources.stringValue(packageName, "release_build_number", "1"),
                supportUrl = resources.stringValue(packageName, "release_support_url", "https://rbdarts.app/support"),
                privacyPolicyUrl = resources.stringValue(packageName, "release_privacy_policy_url", "https://rbdarts.app/privacy"),
                accountDeletionUrl = resources.stringValue(packageName, "release_account_deletion_url", "https://rbdarts.app/account/delete"),
                diagnosticsEnabled = resources.booleanValue(packageName, "release_diagnostics_enabled", true)
            )
        }
    }
}

private fun android.content.res.Resources.stringValue(
    packageName: String,
    name: String,
    fallback: String
): String {
    val identifier = getIdentifier(name, "string", packageName)
    return if (identifier == 0) fallback else getString(identifier)
}

private fun android.content.res.Resources.booleanValue(
    packageName: String,
    name: String,
    fallback: Boolean
): Boolean {
    val identifier = getIdentifier(name, "bool", packageName)
    return if (identifier == 0) fallback else getBoolean(identifier)
}
