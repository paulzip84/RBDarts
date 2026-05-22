import Foundation

public struct StandaloneGameDiagnostics {
    private let reporter: DiagnosticsReporting

    public init(reporter: DiagnosticsReporting = PrivacySafeDiagnostics()) {
        self.reporter = reporter
    }

    public func recordInvalidScore(inning: Int) {
        reporter.record(DiagnosticEvent(
            name: "standalone_invalid_score",
            severity: .warning,
            attributes: ["inning": "\(inning)"]
        ))
    }

    public func recordScoreAccepted(inning: Int) {
        reporter.record(DiagnosticEvent(
            name: "standalone_score_accepted",
            severity: .debug,
            attributes: ["inning": "\(inning)"]
        ))
    }

    public func recordRecovery() {
        reporter.record(DiagnosticEvent(name: "standalone_recovery", severity: .info, attributes: [:]))
    }

    public func recordCompletedGame() {
        reporter.record(DiagnosticEvent(name: "standalone_completed", severity: .info, attributes: [:]))
    }
}
