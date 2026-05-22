package com.rbdarts.core.observability

enum class DiagnosticSeverity { Debug, Info, Warning, Error }

data class DiagnosticEvent(
    val name: String,
    val severity: DiagnosticSeverity,
    val attributes: Map<String, String> = emptyMap()
)

interface DiagnosticsReporter {
    fun record(event: DiagnosticEvent)
}

class PrivacySafeDiagnostics : DiagnosticsReporter {
    private val redactedKeys = listOf("token", "secret", "password", "email", "reason", "providerUserId")
    val events: MutableList<DiagnosticEvent> = mutableListOf()

    override fun record(event: DiagnosticEvent) {
        events += event.copy(
            attributes = event.attributes.filterKeys { key ->
                redactedKeys.none { forbidden -> key.contains(forbidden, ignoreCase = true) }
            }
        )
    }
}
