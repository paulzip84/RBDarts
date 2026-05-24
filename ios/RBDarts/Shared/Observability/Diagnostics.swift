import Foundation

public enum DiagnosticSeverity: String, Sendable {
    case debug
    case info
    case warning
    case error
}

public struct DiagnosticEvent: Equatable, Sendable {
    public let name: String
    public let severity: DiagnosticSeverity
    public let attributes: [String: String]
}

public enum DiagnosticEventName {
    public static let scoreEntryRejected = "score_entry_rejected"
    public static let correctionRequested = "correction_requested"
    public static let correctionRejected = "correction_rejected"
    public static let correctionApplied = "correction_applied"
    public static let gameLocked = "game_locked"
    public static let gameUnlockRequested = "game_unlock_requested"
    public static let substitutionApplied = "substitution_applied"
    public static let baseballStatsViewed = "baseball_stats_viewed"
    public static let baseballProjectionUnavailable = "baseball_projection_unavailable"
}

public protocol DiagnosticsReporting: Sendable {
    func record(_ event: DiagnosticEvent)
}

public final class PrivacySafeDiagnostics: DiagnosticsReporting, @unchecked Sendable {
    public private(set) var events: [DiagnosticEvent] = []
    private let redactedKeys = ["token", "secret", "password", "email", "reason", "providerUserId"]

    public init() {}

    public func record(_ event: DiagnosticEvent) {
        let filtered = event.attributes.filter { key, _ in
            !redactedKeys.contains { key.localizedCaseInsensitiveContains($0) }
        }
        events.append(DiagnosticEvent(name: event.name, severity: event.severity, attributes: filtered))
    }
}
