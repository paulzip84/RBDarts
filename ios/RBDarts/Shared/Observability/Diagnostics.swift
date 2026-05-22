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
